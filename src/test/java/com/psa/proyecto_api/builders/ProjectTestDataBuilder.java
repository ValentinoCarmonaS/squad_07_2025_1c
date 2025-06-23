package com.psa.proyecto_api.builders;

import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.request.UpdateProjectRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.ProjectSummaryResponse;
import com.psa.proyecto_api.dto.response.TaskResponse;
import com.psa.proyecto_api.model.Project;
import com.psa.proyecto_api.model.Task;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import com.psa.proyecto_api.model.enums.ProjectBillingType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;

import static com.psa.proyecto_api.model.enums.ProjectStatus.TRANSITION;
import com.psa.proyecto_api.model.enums.TaskStatus;

import static com.psa.proyecto_api.model.enums.TaskStatus.IN_PROGRESS;

/**
 * Builder class for creating test project data.
 * Provides methods to create and manipulate projects for testing purposes.
 */
public class ProjectTestDataBuilder {

    // ========================================================================
    // CONSTANTS
    // ========================================================================
    
    private static final String DEFAULT_PROJECT_NAME = "Proyecto de Prueba";
    private static final ProjectType DEFAULT_PROJECT_TYPE = ProjectType.DEVELOPMENT;
    private static final ProjectBillingType DEFAULT_PROJECT_BILLING_TYPE = ProjectBillingType.TIME_AND_MATERIAL;
    private static final LocalDate DEFAULT_PROJECT_START_DATE = LocalDate.of(2030, 12, 31);
    private static final LocalDate DEFAULT_PROJECT_END_DATE = LocalDate.of(2030, 6, 30);
    private static final Integer DEFAULT_PROJECT_CLIENT_ID = 1;
    private static final String PROJECTS_ENDPOINT = "/proyectos";

    // ========================================================================
    // INSTANCE FIELDS
    // ========================================================================
    
    private final String baseUrl;
    private final TestRestTemplate restTemplate;
    private final TaskTestDataBuilder taskBuilder;
    private ResponseEntity<ProjectResponse> response;
    private ProjectResponse project;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Constructor for ProjectTestDataBuilder
     * @param baseUrl The base URL for API requests
     * @param restTemplate The REST template for making HTTP requests
     * @param taskBuilder The task builder for creating associated tasks
     */
    public ProjectTestDataBuilder(String baseUrl, TestRestTemplate restTemplate, TaskTestDataBuilder taskBuilder) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.taskBuilder = taskBuilder;
        this.response = null;
        this.project = null;
    }

    // ========================================================================
    // PRIVATE HELPER METHODS
    // ========================================================================
    
    /**
     * Constructs the full URL for an API endpoint
     * @param endpoint The endpoint path to append to the base URL
     * @return The complete URL
     */
    private String url(String endpoint) {
        return baseUrl + endpoint;
    }

    /**
     * Creates a basic project request with minimal default values
     * @return A new project request with minimal defaults
     */
    private CreateProjectRequest createBasicProjectRequest() {
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName(DEFAULT_PROJECT_NAME);
        request.setType(DEFAULT_PROJECT_TYPE);
        request.setBillingType(DEFAULT_PROJECT_BILLING_TYPE);
        request.setStartDate(DEFAULT_PROJECT_START_DATE);
        request.setClientId(DEFAULT_PROJECT_CLIENT_ID);
        return request;
    }

    /**
     * Creates a project request with custom values
     * @param name The project name (optional, uses default if null)
     * @param clientId The client ID (optional, uses default if null)
     * @param type The project type (optional, uses default if null)
     * @param billingType The billing type (optional, uses default if null)
     * @param startDate The start date (optional, uses default if null)
     * @param endDate The end date (optional)
     * @param leaderId The leader ID (optional)
     * @param tagNames The tag names (optional)
     * @return A configured project request
     */
    private CreateProjectRequest createProjectRequest(String name, Integer clientId, 
                                                    ProjectType type, ProjectBillingType billingType,
                                                    LocalDate startDate, LocalDate endDate,
                                                    String leaderId, List<String> tagNames) {
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName(name != null ? name : DEFAULT_PROJECT_NAME);
        request.setClientId(clientId != null ? clientId : DEFAULT_PROJECT_CLIENT_ID);
        request.setType(type != null ? type : DEFAULT_PROJECT_TYPE);
        request.setBillingType(billingType != null ? billingType : DEFAULT_PROJECT_BILLING_TYPE);
        request.setStartDate(startDate != null ? startDate : DEFAULT_PROJECT_START_DATE);
        if (endDate != null) {
            request.setEndDate(endDate);
        }
        if (leaderId != null) {
            request.setLeaderId(leaderId);
        }
        if (tagNames != null) {
            request.setTagNames(tagNames);
        }
        return request;
    }

    /**
     * Validates that a response is successful and contains a body
     * @param response The response to validate
     * @param operation The operation name for error messages
     * @return The response body
     * @throws RuntimeException if the response is not successful or body is null
     */
    private ProjectResponse validateResponse(ResponseEntity<ProjectResponse> response, String operation) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(String.format("Failed to %s. HTTP Status: %s, Body: %s", 
                operation, response.getStatusCode(), response.getBody()));
        }
        
        if (response.getBody() == null) {
            throw new RuntimeException(String.format("%s response body is null", operation));
        }
        
        return response.getBody();
    }

    /**
     * Validates that the current project exists
     * @throws RuntimeException if project is null
     */
    private void validateProjectExists() {
        if (project == null) {
            throw new RuntimeException("Project not found");
        }
    }

    /**
     * Performs an HTTP request and handles common error scenarios
     * @param url The URL to make the request to
     * @param method The HTTP method
     * @param requestBody The request body (can be null)
     * @param operation The operation name for error messages
     * @return The response body
     */
    private ProjectResponse performRequest(String url, HttpMethod method, Object requestBody, String operation) {
        try {
            ResponseEntity<ProjectResponse> response;
            if (requestBody != null) {
                response = restTemplate.exchange(url, method, new HttpEntity<>(requestBody), ProjectResponse.class);
            } else {
                response = restTemplate.exchange(url, method, null, ProjectResponse.class);
            }
            
            return validateResponse(response, operation);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to %s. The API likely returned an error response. " +
                "Original error: %s", operation, e.getMessage()), e);
        }
    }

    /**
     * Changes the status of the current project
     * @param status The new status to set
     */
    private void changeStatus(ProjectStatus status) {
        validateProjectExists();
        
        if (project.getId() == null) {
            throw new RuntimeException("Project not created or has no ID");
        }
        
        switch (status) {
            case INITIATED:
                // INITIATED is the default status, no action needed
                break;
            case IN_PROGRESS:
                taskBuilder.createBasicTaskWithStatus(project.getId(), TaskStatus.IN_PROGRESS.toString());
                break;
            case TRANSITION:
                // For TRANSITION status, we need to create a task and complete it
                taskBuilder.createBasicTaskWithStatus(project.getId(), TaskStatus.DONE.toString());
                break;
            default:
                throw new RuntimeException("Invalid status: " + status);
        }
    }

    // ========================================================================
    // PUBLIC CREATION METHODS
    // ========================================================================
    
    /**
     * Creates a project with the specified parameters
     * @param name The project name (optional, uses default if null)
     * @param clientId The client ID (optional, uses default if null)
     * @param type The project type (optional, uses default if null)
     * @param billingType The billing type (optional, uses default if null)
     * @param startDate The start date (optional, uses default if null)
     * @param endDate The end date (optional)
     * @param status The status to set for the project (optional)
     * @param leaderId The leader ID (optional)
     * @param tagNames The tag names (optional)
     */
    public void createProject(String name, Integer clientId, ProjectType type, 
                            ProjectBillingType billingType, LocalDate startDate, LocalDate endDate,
                            ProjectStatus status, String leaderId, List<String> tagNames) {
        CreateProjectRequest request = createProjectRequest(name, clientId, type, billingType, 
                                                          startDate, endDate, leaderId, tagNames);
        
        String url = url(PROJECTS_ENDPOINT);
        this.project = performRequest(url, HttpMethod.POST, request, "create project");
        
        if (status != null) {
            changeStatus(status);
        }
    }

    /**
     * Creates a basic project with minimal default values
     */
    public void createBasicProject() {
        createProject(null, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a basic project and sets it to the specified status
     * @param status The status to set for the project
     */
    public void createBasicProjectWithStatus(String status) {
        createProject(null, null, null, null, null, null, ProjectStatus.valueOf(status), null, null);
    }

    /**
     * Creates a project with specific start and end dates
     * @param startDate The start date
     * @param endDate The end date
     */
    public void createProject(LocalDate startDate, LocalDate endDate) {
        createProject(null, null, null, null, startDate, endDate, null, null, null);
    }

    /**
     * Creates a project with a specific start date
     * @param startDate The start date
     */
    public void createProject(LocalDate startDate) {
        createProject(null, null, null, null, startDate, null, null, null, null);
    }

    /**
     * Creates a project with tags
     * @param tags The list of tag names
     */
    public void createProjectWithTags(List<String> tags) {
        createProject(null, null, null, null, null, null, null, null, tags);
    }

    /**
     * Creates a project with all specified parameters
     * @param name The project name
     * @param clientId The client ID
     * @param type The project type
     * @param billingType The billing type
     * @param startDate The start date
     * @param endDate The end date
     * @param status The status to set for the project
     * @param leaderId The leader ID
     */
    public void createProject(String name, Integer clientId, ProjectType type,
                            ProjectBillingType billingType, LocalDate startDate, LocalDate endDate,
                            ProjectStatus status, String leaderId) {
        createProject(name, clientId, type, billingType, startDate, endDate, status, leaderId, null);
    }

    // ========================================================================
    // PUBLIC GETTER/UPDATE METHODS
    // ========================================================================
    
    /**
     * Gets the current project after updating it from the server
     * @return The current project response
     */
    public ProjectResponse getProject() {
        updateProject();
        return project;
    }

    /**
     * Gets a project by its ID
     * @param id The project ID
     * @return The project response
     */
    public ProjectResponse getProject(Long id) {
        String url = url(PROJECTS_ENDPOINT + "/" + id);
        this.project = performRequest(url, HttpMethod.GET, null, "get project: " + id);
        return this.project;
    }

    /**
     * Gets all projects
     * @return List of project summary responses
     */
    public List<ProjectSummaryResponse> getProjects() {
        ResponseEntity<List<ProjectSummaryResponse>> response = restTemplate.exchange(
            url(PROJECTS_ENDPOINT),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ProjectSummaryResponse>>() {});
        return response.getBody();
    }

    /**
     * Gets projects filtered by query parameters
     * @param filter The query string (e.g., "?nombre=test&tipo=DEVELOPMENT")
     * @return List of filtered projects
     */
    public List<ProjectSummaryResponse> getProjectsBy(String filter) {
        String url = url(PROJECTS_ENDPOINT + (filter != null ? filter : ""));
        ResponseEntity<List<ProjectSummaryResponse>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ProjectSummaryResponse>>() {});
        return response.getBody();
    }

    /**
     * Updates the current project data from the server
     */
    public void updateProject() {
        if (project != null && project.getId() != null) {
            String url = url(PROJECTS_ENDPOINT + "/" + project.getId());
            this.project = performRequest(url, HttpMethod.GET, null, "update project");
        }
    }

    /**
     * Gets or creates a task with the specified status
     * @param status The task status
     * @return The task response
     */
    public TaskResponse getOrCreateTaskWithStatus(String status) {
        updateProject();
        if (!hasTaskWithStatus(status)) {
            System.out.println("Creating task with status: " + status);
            taskBuilder.createBasicTaskWithStatus(project.getId(), status);
        } else {
            taskBuilder.changeStatus(status);
        }
        return taskBuilder.getTask();
    }

    /**
     * Checks if the project has a task with the specified status
     * @param status The task status to check
     * @return true if a task with the status exists, false otherwise
     */
    public boolean hasTaskWithStatus(String status) {
        updateProject();
        validateProjectExists();
        return project.getTasks().stream()
            .anyMatch(task -> status.equals(task.getStatus().toString()));
    }

    /**
     * Gets a task by its name
     * @param name The task name
     * @return The task response
     */
    public TaskResponse getTaskWithName(String name) {
        updateProject();
        validateProjectExists();
        return project.getTasks().stream()
            .filter(task -> task.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Task not found: " + name));
    }

    /**
     * Gets the current project or creates a basic one if none exists
     * @return The project response
     */
    public ProjectResponse getOrCreateProject() {
        if (project == null) {
            createBasicProject();
        }
        return getProject();
    }

    /**
     * Modifies the current project with the provided request
     * @param request The update request
     */
    public void modifyProject(UpdateProjectRequest request) {
        validateProjectExists();
        String url = url(PROJECTS_ENDPOINT + "/" + project.getId());
        this.project = performRequest(url, HttpMethod.PUT, request, "modify project");
    }

    /**
     * Checks if the last request was successful
     * @return true if the request was successful, false otherwise
     */
    public boolean requestWasSuccesfull() {
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * Removes tags from the current project
     * @param tags The list of tag names to remove
     */
    public void removeTagsFromProject(List<String> tags) {
        validateProjectExists();
        for (String tag : tags) {
            String url = url(PROJECTS_ENDPOINT + "/" + project.getId() + "/tags?tagName=" + tag);
            performRequest(url, HttpMethod.DELETE, null, "remove tag: " + tag);
        }
        updateProject();
    }

    /**
     * Adds tags to the current project
     * @param tags The list of tag names to add
     */
    public void addTagsToProject(List<String> tags) {
        validateProjectExists();
        for (String tag : tags) {
            String url = url(PROJECTS_ENDPOINT + "/" + project.getId() + "/tags");
            performRequest(url, HttpMethod.POST, tag, "add tag: " + tag);
        }
        updateProject();
    }

    // ========================================================================
    // PUBLIC CLEAR METHODS
    // ========================================================================

    /**
     * Clears all projects from the system
     */
    public void clearProjects() {
        List<ProjectSummaryResponse> projects = getProjects();
        for (ProjectSummaryResponse project : projects) {
            deleteProject(project.getId());
        }
    }

    /**
     * Deletes a project by its ID
     * @param id The project ID to delete
     */
    public void deleteProject(Long id) {
        String url = url(PROJECTS_ENDPOINT + "/" + id);
        performRequest(url, HttpMethod.DELETE, null, "delete project: " + id);
    }
}