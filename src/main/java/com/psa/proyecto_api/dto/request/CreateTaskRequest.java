package com.psa.proyecto_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateTaskRequest {
    
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;

    @NotNull(message = "El proyecto es obligatorio")
    private Long projectId;
    
    @Min(value = 0, message = "Las horas estimadas no pueden ser negativas")
    private Integer estimatedHours;
    
    private Integer assignedResourceId;
    
    private List<String> tagNames;
}
