package com.psa.proyecto_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UpdateTaskRequest {
    
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @Min(value = 0, message = "Las horas estimadas no pueden ser negativas")
    private Integer estimatedHours;
    
    private Integer assignedResourceId;
}
