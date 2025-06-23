package com.psa.proyecto_api.builders;

import java.util.List;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.psa.proyecto_api.dto.request.CreateTaskRequest;
import com.psa.proyecto_api.dto.response.TaskResponse;
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
    
    private String baseUrl;
    private TestRestTemplate restTemplate;
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
     * Sets minimal default values for a task request
     * @param request The task request to populate
     * @return The populated task request with minimal defaults
     */
    private CreateTaskRequest setMinimalDefaults(CreateTaskRequest request) {
        request.setName(DEFAULT_TASK_NAME);
        request.setEstimatedHours(DEFAULT_ESTIMATED_HOURS);
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

    // ========================================================================
    // PUBLIC TASK STATUS METHODS
    // ========================================================================
    
    /**
     * Changes the status of the current task
     * @param status The new status to set
     */
    public void changeStatus(String status) {
        updateTask();
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        if (status.equals(task.getStatus().toString())) {
            return;
        }
        TaskStatus newStatus = TaskStatus.valueOf(status);
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
    }

    /**
     * Activates a task, changing its status to IN_PROGRESS
     * @param task The task to activate
     */
    public void activate(TaskResponse task) {
        String url = url(TASKS_ENDPOINT + "/" + task.getId() + ACTIVATE_ENDPOINT);
        try {
            response = restTemplate.exchange(
                url, HttpMethod.PUT, null, TaskResponse.class);
            this.task = response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate task. The API likely returned an error response. " +
                "Original error: " + e.getMessage(), e);
        }
    }

    /**
     * Deactivates a task, changing its status to DONE
     * @param task The task to deactivate
     */
    public void deactivate(TaskResponse task) {
        String url = url(TASKS_ENDPOINT + "/" + task.getId() + DEACTIVATE_ENDPOINT);
        try {
            response = restTemplate.exchange(
                url, HttpMethod.PUT, null, TaskResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Task deactivation failed with status: " + response.getStatusCode());
            }
            
            this.task = response.getBody();
            if (this.task == null) {
                throw new RuntimeException("Task deactivation returned null");
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error deactivating task", e);
        }
    }

    // ========================================================================
    // PUBLIC CREATION METHODS
    // ========================================================================
    
    /**
     * Creates a basic task with minimal default values
     * @param projectId The ID of the project to create the task for
     */
    public void createBasicTask(Long projectId) {
        CreateTaskRequest request = new CreateTaskRequest();
        request = setMinimalDefaults(request);
        
        try {
            response = restTemplate.postForEntity(
                url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT), request, TaskResponse.class);
            
            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new RuntimeException("Failed to create task. HTTP Status: " + response.getStatusCode() + 
                    ", Body: " + response.getBody());
            }
            
            if (response.getBody() == null) {
                throw new RuntimeException("Task response body is null");
            }
            
            task = response.getBody();
        } catch (Exception e) {
            // If we get a deserialization error, it's likely because the API returned an error response
            // with a status field containing the HTTP status code instead of a TaskStatus enum
            throw new RuntimeException("Failed to create task. The API likely returned an error response. " +
                "Original error: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a basic task and sets it to the specified status
     * @param projectId The ID of the project to create the task for
     * @param status The status to set for the task
     */
    public void createBasicTaskWithStatus(Long projectId, String status) {
        createBasicTask(projectId);
        changeStatus(status);
    }

    /**
     * Creates a task with a specific name and status
     * @param name The name for the task
     * @param projectId The ID of the project to create the task for
     * @param status The status to set for the task
     */
    public void createTaskWithStatus(String name, Long projectId, String status) {
        CreateTaskRequest request = new CreateTaskRequest();
        request = setMinimalDefaults(request);
        request.setName(name);
        response = restTemplate.postForEntity(url(
            PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT), request, TaskResponse.class);
        this.task = response.getBody();
        System.out.println("Task created: " + this.task);
        changeStatus(status);
    }

    public void createTask(Long projectId, String name, String status, String ticketId) {
        CreateTaskRequest request = new CreateTaskRequest();
        request = setMinimalDefaults(request);
        request.setName(name);
        request.setTicketId(Integer.parseInt(ticketId));
        response = restTemplate.postForEntity(url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT), request, TaskResponse.class);
        this.task = response.getBody();
        changeStatus(status);
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
     * Updates the current task data from the server
     */
    public void updateTask() {
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        String url = url(TASKS_ENDPOINT + "/" + task.getId());
        response = restTemplate.getForEntity(url, TaskResponse.class);
        task = response.getBody();
    }

    public List<TaskResponse> getTasksBy(Long projectId, String filter) {
        String url = url(PROJECTS_ENDPOINT + "/" + projectId + TASKS_ENDPOINT + filter);
        ResponseEntity<List<TaskResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TaskResponse>>() {});
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get tasks. Status: " + response.getStatusCode());
        }

        List<TaskResponse> taskResponses = response.getBody();
        return taskResponses;
    }
}
