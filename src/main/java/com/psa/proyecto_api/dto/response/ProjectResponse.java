package com.psa.proyecto_api.dto.response;

import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private Integer clientId;
    private ProjectType type;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer leaderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private List<String> tagNames;
    private List<TaskSummaryResponse> tasks;

    // Información adicional calculada
    private Long totalTasks;
    private Long completedTasks;
    private Double progressPercentage;
}
