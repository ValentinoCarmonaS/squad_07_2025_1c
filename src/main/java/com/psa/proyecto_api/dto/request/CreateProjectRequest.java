package com.psa.proyecto_api.dto.request;

import com.psa.proyecto_api.model.enums.ProjectType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateProjectRequest {
    
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;
    
    @NotNull(message = "El cliente es obligatorio")
    private Integer clientId;
    
    @NotNull(message = "El tipo de proyecto es obligatorio")
    private ProjectType type;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    private LocalDate startDate;
    
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDate endDate;
    
    private Integer leaderId;   
}
