package com.psa.proyecto_api.dto.response;

import com.psa.proyecto_api.model.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private Long projectId;
    private TaskStatus status;
    private LocalDate dueDate;
    private Integer estimatedHours;
    private Integer assignedResourceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private List<String> tagNames;
    
    // Información adicional
    private boolean isOverdue;
    private Integer daysUntilDue;
}
