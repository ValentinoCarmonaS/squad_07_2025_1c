package com.psa.proyecto_api.model.enums;

/**
 * Enumeración que define los posibles estados de un proyecto.
 */
public enum ProjectStatus {

    /**
     * Proyecto iniciado, pero no en progreso.
     */
    INITIATED("Iniciado"),

    /**
     * Proyecto en progreso activo.
     */
    IN_PROGRESS("En Progreso"),

    /**
     * Que significa que el proyecto este en Transición?
     */
    TRANSITION("Transición");
    
    private final String displayName;
    
    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Verifica si el estado representa un proyecto iniciado.
     */
    public boolean isInitiated() {
        return this == INITIATED;
    }

    /**
     * Verifica si el estado representa un proyecto activo.
     */
    public boolean isActive() {
        return this == IN_PROGRESS;
    }

    /**
     * Verifica si el estado representa un proyecto en transición.
     */
    public boolean isTransition() {
        return this == TRANSITION;
    }
}
