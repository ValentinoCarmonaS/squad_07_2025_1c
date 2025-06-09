package com.psa.proyecto_api.model.enums;

import com.psa.proyecto_api.exception.OperationNotAllowedException;

/**
 * Enumeración que define los tipos de proyecto.
 */
public enum ProjectType {
    /**
     * Proyecto de desarrollo de software.
     */
    DEVELOPMENT("Desarrollo"),
    
    /**
     * Proyecto de implementación de sistemas.
     */
    IMPLEMENTATION("Implementación");
    
    private final String displayName;
    
    ProjectType(String displayName) {
        this.displayName = displayName;
    }

    public static ProjectType fromString(String value) {
        if (value == null) {
            return null;
        }
        
        for (ProjectType type : ProjectType.values()) {
            if (type.displayName.equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        
        throw new OperationNotAllowedException("Tipo de proyecto no válido: " + value);
    }

    
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Verifica si el tipo de proyecto es de desarrollo.
     */
    public boolean isDevelopment() {
        return this == DEVELOPMENT;
    }

    /**
     * Verifica si el tipo de proyecto es de implementación.
     */
    public boolean isImplementation() {
        return this == IMPLEMENTATION;
    }
}
