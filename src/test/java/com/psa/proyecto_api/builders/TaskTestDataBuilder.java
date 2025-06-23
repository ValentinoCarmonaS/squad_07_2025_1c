package com.psa.proyecto_api.builders;

import java.util.List;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.psa.proyecto_api.dto.request.CreateTaskRequest;
import com.psa.proyecto_api.dto.request.UpdateTaskRequest;
import com.psa.proyecto_api.dto.response.TaskResponse;
import com.psa.proyecto_api.dto.response.TaskSummaryResponse;
import com.psa.proyecto_api.model.Task;
import com.psa.proyecto_api.model.enums.TaskStatus;

/**
 * Builder class for creating and managing tasks in tests.
 * Provides methods to create, update, and manipulate tasks for testing purposes.
 */
public class TaskTestDataBuilder {

    // ========================================================================
    // CONSTANTS
    // ========================================================================
    
    private static final String DEFAULT_TASK_NAME = "Tarea de Prueba";
    private static final Integer DEFAULT_ESTIMATED_HOURS = 1;
    private static final Long DEFAULT_RESOURCE_ID = 1L;
    private static final String ACTIVATE_ENDPOINT = "/activar";
    private static final String DEACTIVATE_ENDPOINT = "/desactivar";
    private static final String TASKS_ENDPOINT = "/tareas";
    private static final String PROJECTS_ENDPOINT = "/proyectos";

    // ========================================================================
    // INSTANCE FIELDS
    // ========================================================================
    
    private final String baseUrl;
    private final TestRestTemplate restTemplate;
    private ResponseEntity<TaskResponse> response;
    private TaskResponse task;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Constructor for TaskTestDataBuilder
     * @param baseUrl The base URL for API requests
     * @param restTemplate The REST template for making HTTP requests
     */
    public TaskTestDataBuilder(String baseUrl, TestRestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.response = null;
        this.task = null;
    }

    // ========================================================================
    // PRIVATE HELPER METHODS
    // ========================================================================
    
    /**
     * Creates a basic task request with minimal default values
     * @return A new task request with minimal defaults
     */
    private CreateTaskRequest createBasicTaskRequest() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setName(DEFAULT_TASK_NAME);
        request.setEstimatedHours(DEFAULT_ESTIMATED_HOURS);
        return request;
    }

    /**
     * Creates a task request with custom values
     * @param name The task name
     * @param estimatedHours The estimated hours
     * @param ticketId The ticket ID (optional)
     * @param assignedResourceId The assigned resource ID (optional)
     * @return A configured task request
     */
    private CreateTaskRequest createTaskRequest(String name, Integer estimatedHours, 
                                              Integer ticketId, String assignedResourceId) {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setName(name != null ? name : DEFAULT_TASK_NAME);
        request.setEstimatedHours(estimatedHours != null ? estimatedHours : DEFAULT_ESTIMATED_HOURS);
        if (ticketId != null) {
            request.setTicketId(ticketId);
        }
        if (assignedResourceId != null) {
            request.setAssignedResourceId(assignedResourceId);
        }
        return request;
    }

    /**
     * Constructs the full URL for an API endpoint
     * @param endpoint The endpoint path to append to the base URL
     * @return The complete URL
     */
    private String url(String endpoint) {
        return baseUrl + endpoint;
    }

    /**
     * Validates that a response is successful and contains a body
     * @param response The response to validate
     * @param operation The operation name for error messages
     * @return The response body
     * @throws RuntimeException if the response is not successful or body is null
     */
    private TaskResponse validateResponse(ResponseEntity<TaskResponse> response, String operation) {
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
     * Validates that the current task exists
     * @throws RuntimeException if task is null
     */
    private void validateTaskExists() {
        if (task == null) {
            throw new RuntimeException("Task not found");
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
    private TaskResponse performRequest(String url, HttpMethod method, Object requestBody, String operation) {
        try {
            ResponseEntity<TaskResponse> response;
            if (requestBody != null) {
                response = restTemplate.exchange(url, method, new HttpEntity<>(requestBody), TaskResponse.class);
            } else {
                response = restTemplate.exchange(url, method, null, TaskResponse.class);
            }
            
            return validateResponse(response, operation);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to %s. The API likely returned an error response. " +
                "Original error: %s", operation, e.getMessage()), e);
        }
    }

    // ========================================================================
    // PUBLIC TASK STATUS METHODS
    // ========================================================================
    
    /**
     * Changes the status of the current task
     * @param status The new status to set
     */
    public void changeStatus(String status) {
        updateTask();
        validateTaskExists();
        
        if (status.equals(task.getStatus().toString())) {
            return;
        }
        
        TaskStatus newStatus = TaskStatus.valueOf(status);
        try {
            switch (newStatus) {
                case IN_PROGRESS:
                    activate(task);
                    break;
                case DONE:
                    if (task.getStatus() != TaskStatus.IN_PROGRESS) {
                        activate(task);
                    }
                    deactivate(task);
                    break;
                default:
                    throw new RuntimeException("Invalid status: " + status);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to change task status to '" + status + "'. " + e.getMessage(), e);
        }
    }

    /**
     * Activates a task, changing its status to IN_PROGRESS
     * @param task The task to activate
     */
    public void activate(TaskResponse task) {
        String url = url(TASKS_ENDPOINT + "/" + task.getId() + ACTIVATE_ENDPOINT);
        this.task = performRequest(url, HttpMethod.PUT, null, "activate task");
    }

    /**
     * Deactivates a task, changing its status to DONE
     * @param task The task to deactivate
     */
    public void deactivate(TaskResponse task) {
        String url = url(TASKS_ENDPOINT + "/" + task.getId() + DEACTIVATE_ENDPOINT);
        this.task = performRequest(url, HttpMethod.PUT, null, "deactivate task");
    }

    // ========================================================================
    // PUBLIC CREATION METHODS
    // ========================================================================
    
    /**
     * Creates a task with the specified parameters
     * @param projectId The ID of the project to create the task for
     * @param name The task name (optional, uses default if null)
     * @param status The status to set for the task (optional)
     * @param ticketId The ticket ID (optional)
     * @param estimatedHours The estimated hours (optional, uses default if null)
     * @param assignedResourceId The assigned resource ID (optional)
     */
    public void createTask(Long projectId, String name, String status, 
                          String ticketId, String estimatedHours, String assignedResourceId) {
        Integer ticketIdInt = ticketId != null ? Integer.parseInt(ticketId) : null;
        Integer estimatedHoursInt = estimatedHours != null ? Integer.parseInt(estimatedHours) : null;
        
        CreateTaskRequest request = createTaskRequest(name, estimatedHoursInt, ticketIdInt, assignedResourceId);
        
        String url = url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT);
        this.task = performRequest(url, HttpMethod.POST, request, "create task");
        
        if (status != null) {
            changeStatus(status);
        }
    }

    /**
     * Creates a basic task with minimal default values
     * @param projectId The ID of the project to create the task for
     */
    public void createBasicTask(Long projectId) {
        createTask(projectId, null, null, null, null, null);
    }

    /**
     * Creates a basic task and sets it to the specified status
     * @param projectId The ID of the project to create the task for
     * @param status The status to set for the task
     */
    public void createBasicTaskWithStatus(Long projectId, String status) {
        createTask(projectId, null, status, null, null, null);
    }

    /**
     * Creates a task with a specific name and status
     * @param name The name for the task
     * @param projectId The ID of the project to create the task for
     * @param status The status to set for the task
     */
    public void createTaskWithStatus(String name, Long projectId, String status) {
        createTask(projectId, name, status, null, null, null);
    }

    /**
     * Creates a task with ticket ID and status
     * @param projectId The ID of the project to create the task for
     * @param name The name for the task
     * @param status The status to set for the task
     * @param ticketId The ticket ID
     */
    public void createTask(Long projectId, String name, String status, String ticketId) {
        createTask(projectId, name, status, ticketId, null, null);
    }

    /**
     * Creates a task with ticket ID, status, and assigned resource
     * @param projectId The ID of the project to create the task for
     * @param name The name for the task
     * @param status The status to set for the task
     * @param ticketId The ticket ID
     * @param assignedResourceId The assigned resource ID
     */
    public void createTask(Long projectId, String name, String status, String ticketId, String assignedResourceId) {
        createTask(projectId, name, status, ticketId, null, assignedResourceId);
    }

    /**
     * Creates a task with an assigned resource
     * @param projectId The ID of the project to create the task for
     * @param resourceId The resource ID to assign
     */
    public void createTaskWithAssignedResource(Long projectId, String resourceId) {
        createTask(projectId, null, null, null, null, resourceId);
    }

    // ========================================================================
    // PUBLIC GETTER METHODS
    // ========================================================================
    
    /**
     * Gets the current task after updating it from the server
     * @return The current task response
     */
    public TaskResponse getTask() {
        updateTask();
        return task;
    }

    /**
     * Gets a task by its ID
     * @param id The task ID
     * @return The task response
     */
    public TaskResponse getTask(Long id) {
        String url = url(TASKS_ENDPOINT + "/" + id);
        this.task = performRequest(url, HttpMethod.GET, null, "get task: " + id);
        return this.task;
    }

    /**
     * Updates the current task data from the server
     */
    public void updateTask() {
        validateTaskExists();
        String url = url(TASKS_ENDPOINT + "/" + task.getId());
        this.task = performRequest(url, HttpMethod.GET, null, "update task");
    }

    /**
     * Gets tasks by project ID with optional filter
     * @param projectId The project ID
     * @param filter The filter string (optional)
     * @return List of task responses
     */
    public List<TaskResponse> getTasksBy(Long projectId, String filter) {
        String url = url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT + (filter != null ? filter : ""));
        ResponseEntity<List<TaskResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TaskResponse>>() {});
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get tasks. Status: " + response.getStatusCode());
        }

        return response.getBody();
    }

    /**
     * Gets task summaries by project ID with optional filter
     * @param projectId The project ID
     * @param filter The filter string (optional)
     * @return List of task summary responses
     */
    public List<TaskSummaryResponse> getTasksSummaryBy(Long projectId, String filter) {
        String url = url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT + (filter != null ? filter : ""));
        ResponseEntity<List<TaskSummaryResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TaskSummaryResponse>>() {});
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get tasks. Status: " + response.getStatusCode());
        }

        return response.getBody();
    }

    // ========================================================================
    // PUBLIC DELETE METHODS
    // ========================================================================

    /**
     * Clears all tasks from the system
     */
    public void clearTasks() {
        String url = url(TASKS_ENDPOINT);
        ResponseEntity<List<TaskResponse>> response = restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskResponse>>() {});
        
        List<TaskResponse> tasks = response.getBody();
        if (tasks != null) {
            for (TaskResponse task : tasks) {
                restTemplate.exchange(
                    url(TASKS_ENDPOINT + "/" + task.getId()),
                    HttpMethod.DELETE,
                    null,
                    Void.class
                );
            }
        }
    }

    // ========================================================================
    // PUBLIC MODIFICATION METHODS
    // ========================================================================

    /**
     * Assigns a resource to a task
     * @param taskId The task ID
     * @param resourceId The resource ID to assign
     */
    public void assignResourceToTask(Long taskId, String resourceId) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setAssignedResourceId(resourceId);
        
        String url = url(TASKS_ENDPOINT + "/" + taskId);
        this.task = performRequest(url, HttpMethod.PUT, request, "assign resource to task");
    }

    /**
     * Modifies the estimated hours for a task
     * @param taskId The task ID
     * @param estimatedHours The new estimated hours
     */
    public void modifyEstimatedHours(Long taskId, int estimatedHours) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setEstimatedHours(estimatedHours);
        
        String url = url(TASKS_ENDPOINT + "/" + taskId);
        this.task = performRequest(url, HttpMethod.PUT, request, "modify estimated hours");
    }
}
