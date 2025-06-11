package com.psa.proyecto_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateTaskRequest {
    
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @NotNull(message = "Las horas estimadas son obligatorias")
    @Min(value = 1, message = "Las horas estimadas no pueden ser negativas o cero")
    private Integer estimatedHours;
    
    @Size(min = 36, max = 36, message = "El id del recurso debe tener 36 caracteres")
    private String assignedResourceId;
    
    private List<String> tagNames;
}
