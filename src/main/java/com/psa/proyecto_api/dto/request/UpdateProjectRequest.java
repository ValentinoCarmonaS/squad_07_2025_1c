package com.psa.proyecto_api.dto.request;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateProjectRequest {
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres") 
    private String name;
    
    private Integer clientId;
    
    private ProjectType type;
    
    private ProjectBillingType billingType;
    
    private LocalDate startDate;
    
    @FutureOrPresent(message = "La fecha de fin debe ser futura")
    private LocalDate endDate;
    
    private Integer leaderId;
}
