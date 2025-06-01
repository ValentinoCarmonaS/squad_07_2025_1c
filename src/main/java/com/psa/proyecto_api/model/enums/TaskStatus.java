package com.psa.proyecto_api.model.enums;

/**
 * Enumeración que define los posibles estados de una tarea.
 */
public enum TaskStatus {
    /**
     * Tarea pendiente por hacer.
     */
    TO_DO("Por Hacer"),
    
    /**
     * Tarea en progreso.
     */
    IN_PROGRESS("En Progreso"),
    
    /**
     * Tarea completada.
     */
    DONE("Completada");
    
    private final String displayName;
    
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Verifica si el estado representa una tarea activa.
     */
    public boolean isActive() {
        return this == TO_DO || this == IN_PROGRESS;
    }
    
    /**
     * Verifica si la tarea puede ser iniciada desde este estado.
     */
    public boolean canStart() {
        return this == TO_DO;
    }
    
    /**
     * Verifica si la tarea puede ser completada desde este estado.
     */
    public boolean canComplete() {
        return this == TO_DO || this == IN_PROGRESS;
    }
}

