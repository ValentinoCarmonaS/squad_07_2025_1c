package com.psa.proyecto_api.dto.response;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectResponse {
    private Long id;
    private String name;
    private Integer clientId;
    private Integer leaderId;
    private ProjectType type;
    private ProjectBillingType billingType;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer estimatedHours;
    
    private List<String> tagNames;
    private List<TaskSummaryResponse> tasks;

    // Información adicional calculada
    private Long totalTasks;
    private Long completedTasks;
    private Double progressPercentage;
}
