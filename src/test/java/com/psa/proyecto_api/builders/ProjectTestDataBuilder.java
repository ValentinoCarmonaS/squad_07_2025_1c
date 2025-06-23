package com.psa.proyecto_api.builders;

import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.request.UpdateProjectRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
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

    private static final String DEFAULT_PROJECT_NAME = "Proyecto de Prueba";
    private static final ProjectType DEFAULT_PROJECT_TYPE = ProjectType.DEVELOPMENT;
    private static final ProjectBillingType DEFAULT_PROJECT_BILLING_TYPE = ProjectBillingType.TIME_AND_MATERIAL;
    private static final LocalDate DEFAULT_PROJECT_START_DATE = LocalDate.of(2030, 12, 31);
    private static final LocalDate DEFAULT_PROJECT_END_DATE = LocalDate.of(2030, 6, 30);
    private static final Integer DEFAULT_PROJECT_CLIENT_ID = 1;

    private static final String PROJECTS_ENDPOINT = "/proyectos";

    private String baseUrl;
    private TestRestTemplate restTemplate;
    
    private ResponseEntity<ProjectResponse> response;
    private ProjectResponse project;
    private TaskTestDataBuilder taskBuilder;

    // Private helper methods
    
    /**
     * Constructs the full URL for an API endpoint
     * @param endpoint The endpoint path to append to the base URL
     * @return The complete URL
     */
    private String url(String endpoint) {
        return baseUrl + endpoint;
    }

    /**
     * Sets all default values for a project request
     * @param request The project request to populate
     * @return The populated project request
     */
    private CreateProjectRequest setAllDefaults(CreateProjectRequest request) {
        request.setName(DEFAULT_PROJECT_NAME);
        request.setType(DEFAULT_PROJECT_TYPE);
        request.setBillingType(DEFAULT_PROJECT_BILLING_TYPE);
        request.setStartDate(DEFAULT_PROJECT_START_DATE);
        request.setEndDate(DEFAULT_PROJECT_END_DATE);
        request.setClientId(DEFAULT_PROJECT_CLIENT_ID);
        return request;
    }
    
    /**
     * Sets minimal required default values for a project request
     * @param request The project request to populate
     * @return The populated project request with minimal defaults
     */
    private CreateProjectRequest setMinimalDefaults(CreateProjectRequest request) {
        request.setName(DEFAULT_PROJECT_NAME);
        request.setType(DEFAULT_PROJECT_TYPE);
        request.setBillingType(DEFAULT_PROJECT_BILLING_TYPE);
        request.setStartDate(DEFAULT_PROJECT_START_DATE);
        request.setClientId(DEFAULT_PROJECT_CLIENT_ID);
        return request;
    }

    /**
     * Changes the status of the current project
     * @param status The new status to set
     */
    private void changeStatus(ProjectStatus status) {
        if (project == null || project.getId() == null) {
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

    /**
     * Creates a basic project with minimal default values
     */
    public void createBasicProject() {
        CreateProjectRequest request = new CreateProjectRequest();
        request = setMinimalDefaults(request);
        
        try {
            response = restTemplate.postForEntity(url(PROJECTS_ENDPOINT), request, ProjectResponse.class);
            
            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
            
            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new RuntimeException("Failed to create project. Status: " + response.getStatusCode() + 
                    ", Body: " + response.getBody());
            }
            
            project = response.getBody();
            if (project == null) {
                throw new RuntimeException("Project response body is null");
            }
        } catch (Exception e) {
            System.err.println("Error creating project: " + e.getMessage());
            throw new RuntimeException("Failed to create project", e);
        }
    }

    // Constructor

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

    // Public creation methods

    /**
     * Creates a basic project and sets it to the specified status
     * @param status The status to set for the project
     */
    public void createBasicProjectWithStatus(String status) {
        createBasicProject();
        changeStatus(ProjectStatus.valueOf(status));
    }

    // Public getter/update methods

    /**
     * Gets the current project after updating it from the server
     * @return The current project response
     */
    public ProjectResponse getProject() {
        updateProject();
        return project;
    }

    /**
     * Updates the current project data from the server
     */
    public void updateProject() {
        if (project != null) {
            String url = url(PROJECTS_ENDPOINT + "/" + project.getId());
            response = restTemplate.getForEntity(url, ProjectResponse.class);
            project = response.getBody();
        }
    }

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

    public boolean hasTaskWithStatus(String status) {
        updateProject();
        if (project == null) {
            throw new RuntimeException("Project not found");
        }
        return project.getTasks().stream()
            .anyMatch(task -> status.equals(task.getStatus().toString()));
    }

    public TaskResponse getTaskWithName(String name) {
        updateProject();
        if (project == null) {
            throw new RuntimeException("Project not found");
        }
        return project.getTasks().stream()
            .filter(task -> task.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Task not found: " + name));
    }

    public ProjectResponse getOrCreateProject() {
        if (project == null) {
            createBasicProject();
        }
        return getProject();
    }

    public void createProject(LocalDate startDate, LocalDate endDate) {
        CreateProjectRequest request = new CreateProjectRequest();
        request = setMinimalDefaults(request);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        response = restTemplate.postForEntity(url(PROJECTS_ENDPOINT), request, ProjectResponse.class);
        project = response.getBody();
    }

    public void createProject(LocalDate startDate) {
        CreateProjectRequest request = new CreateProjectRequest();
        request = setMinimalDefaults(request);
        request.setStartDate(startDate);
        response = restTemplate.postForEntity(url(PROJECTS_ENDPOINT), request, ProjectResponse.class);
        project = response.getBody();
    }


    public void modifyProject(UpdateProjectRequest request) {
        response = restTemplate.exchange(
            url(PROJECTS_ENDPOINT + "/" + project.getId()),
            HttpMethod.PUT,
            new HttpEntity<>(request),
            ProjectResponse.class);
        project = response.getBody();
    }

    public boolean requestWasSuccesfull() {
        return response.getStatusCode().is2xxSuccessful();
    }

    public void createProjectWithTags(List<String> tags) {
        CreateProjectRequest request = new CreateProjectRequest();
        request = setMinimalDefaults(request);
        request.setTagNames(tags);
        response = restTemplate.postForEntity(url(PROJECTS_ENDPOINT), request, ProjectResponse.class);
        project = response.getBody();
    }

    public void removeTagsFromProject(List<String> tags) {
        updateProject();
        for (String tag : tags) {
            try {
            response = restTemplate.exchange(
                url(PROJECTS_ENDPOINT + "/" + project.getId() + "/tags?tagName=" + tag),
                HttpMethod.DELETE,
                    null,
                    ProjectResponse.class);
            } catch (Exception e) {
                throw new RuntimeException("Error removing tag: " + e.getMessage());
            }
        }
    }

    public void addTagsToProject(List<String> tags) {
        updateProject();
        
        String tagRequest = tags.stream().collect(Collectors.joining(","));
        
        try {
            response = restTemplate.postForEntity(
                url(PROJECTS_ENDPOINT + "/" + project.getId() + "/tags"),
                tagRequest,
                ProjectResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error adding tag: " + e.getMessage());
        }
    }
}