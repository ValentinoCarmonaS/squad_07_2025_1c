package com.psa.proyecto_api.dto.response;

import com.psa.proyecto_api.model.enums.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskSummaryResponse {
    private Long id;
    private String name;
    private Long projectId;
    private TaskStatus status;
    private Integer estimatedHours;
    private Integer assignedResourceId;
    private List<String> tagNames;
}
