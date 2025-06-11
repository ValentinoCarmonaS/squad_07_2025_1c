package com.psa.proyecto_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @Min(value = 0, message = "Las horas estimadas no pueden ser negativas")
    private Integer estimatedHours;
    
    @Size(min = 36, max = 36, message = "El id del recurso debe tener 36 caracteres")
    private String assignedResourceId;
}
