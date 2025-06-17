package com.psa.proyecto_api.builders;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.psa.proyecto_api.dto.request.CreateTaskRequest;
import com.psa.proyecto_api.dto.response.TaskResponse;
import com.psa.proyecto_api.model.enums.TaskStatus;

public class TaskTestDataBuilder {
    
    private final TestRestTemplate restTemplate;
    private final String baseUrl;
    
    public TaskTestDataBuilder(String baseUrl, TestRestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }
    
    public TaskResponse createDefaultTask() {
        return createTask("Tarea por defecto", 10);
    }
    
    public TaskResponse createTask(String name, int estimatedHours) {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setName(name);
        request.setEstimatedHours(estimatedHours);

        try {
            ResponseEntity<TaskResponse> response = restTemplate.postForEntity(
                baseUrl, request, TaskResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Task creation failed with status: " + response.getStatusCode());
            }
            
            TaskResponse task = response.getBody();
            if (task == null) {
                throw new RuntimeException("Task creation returned null");
            }
            
            return task;
        } catch (Exception e) {
            throw new RuntimeException("Error creating task: " + name, e);
        }
    }

    public TaskResponse createTaskWithStatus(String name, int estimatedHours, TaskStatus targetStatus) {
        TaskResponse task = createTask(name, estimatedHours);
        
        switch (targetStatus) {
            case TO_DO:
                return task;
                
            case IN_PROGRESS:
                return activate(task);
                
            case DONE:
                task = activate(task);
                return deactivate(task);
                
            default:
                throw new IllegalArgumentException("Unsupported status: " + targetStatus);
        }
    }

    public TaskResponse getTask(Long id) {
        String url = getTaskBaseUrl() + "/" + id;
        try {
            ResponseEntity<TaskResponse> response = restTemplate.getForEntity(url, TaskResponse.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to get task with status: " + response.getStatusCode());
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving task with ID: " + id, e);
        }
    }

    public TaskResponse setStatus(TaskResponse task, TaskStatus targetStatus) {
        if (task.getStatus().equals(targetStatus)) {
            return task;
        }

        try {
            switch (targetStatus) {
                case TO_DO:
                    throw new IllegalArgumentException("Cannot set status to TO_DO via API - not supported by business rules");
                    
                case IN_PROGRESS:
                    if (task.getStatus() == TaskStatus.TO_DO) {
                        return activate(task);
                    } else {
                        throw new IllegalArgumentException("Can only activate tasks from TO_DO status");
                    }
                    
                case DONE:
                    if (task.getStatus() == TaskStatus.TO_DO) {
                        task = activate(task);
                        return deactivate(task);
                    } else if (task.getStatus() == TaskStatus.IN_PROGRESS) {
                        return deactivate(task);
                    } else {
                        throw new IllegalArgumentException("Can only complete tasks from IN_PROGRESS status");
                    }
                    
                default:
                    throw new IllegalArgumentException("Unsupported status: " + targetStatus);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error setting task status to: " + targetStatus, e);
        }
    }

    public TaskResponse activate(TaskResponse task) {
        String url = getTaskBaseUrl() + "/" + task.getId() + "/activar";
        try {
            ResponseEntity<TaskResponse> response = restTemplate.exchange(
                url, HttpMethod.PUT, null, TaskResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Task activation failed with status: " + response.getStatusCode());
            }
            
            TaskResponse updatedTask = response.getBody();
            if (updatedTask == null) {
                throw new RuntimeException("Task activation returned null");
            }
            
            return updatedTask;
        } catch (Exception e) {
            throw new RuntimeException("Error activating task", e);
        }
    }

    public TaskResponse deactivate(TaskResponse task) {
        String url = getTaskBaseUrl() + "/" + task.getId() + "/desactivar";
        try {
            ResponseEntity<TaskResponse> response = restTemplate.exchange(
                url, HttpMethod.PUT, null, TaskResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Task deactivation failed with status: " + response.getStatusCode());
            }
            
            TaskResponse updatedTask = response.getBody();
            if (updatedTask == null) {
                throw new RuntimeException("Task deactivation returned null");
            }
            
            return updatedTask;
        } catch (Exception e) {
            throw new RuntimeException("Error deactivating task", e);
        }
    }
    
    private String getTaskBaseUrl() {
        return baseUrl.replaceAll("/proyectos/\\d+/tareas", "/tareas");
    }
}