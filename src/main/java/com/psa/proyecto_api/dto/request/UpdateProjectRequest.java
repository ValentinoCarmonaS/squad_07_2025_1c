package com.psa.proyecto_api.dto.request;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateProjectRequest {
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres") // Fixed
    private String name;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;
    
    private Integer clientId;
    
    @NotNull(message = "El tipo de proyecto es obligatorio")
    private ProjectType type;
    
    @NotNull(message = "El tipo de facturación es obligatorio")
    private ProjectBillingType billingType;
    
    @NotNull(message = "El estado es obligatorio")
    private ProjectStatus status;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @Positive(message = "Las horas estimadas deben ser positivas")
    private Integer estimatedHours;
    
    private Integer leaderId;
    
    private List<String> tagNames;
}
