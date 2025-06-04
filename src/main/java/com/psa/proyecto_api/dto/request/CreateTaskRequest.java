package com.psa.proyecto_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateTaskRequest {
    
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;
    
    private LocalDate dueDate;
    
    @Min(value = 0, message = "Las horas estimadas no pueden ser negativas")
    private Integer estimatedHours;
    
    private Integer assignedResourceId;
    
    private List<String> tagNames;
}
