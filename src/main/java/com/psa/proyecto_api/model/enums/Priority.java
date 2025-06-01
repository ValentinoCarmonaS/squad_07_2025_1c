package com.psa.proyecto_api.model.enums;

/**
 * Enumeración que define los niveles de prioridad de una tarea.
 */
public enum Priority {
    /**
     * Prioridad baja.
     */
    LOW("Baja", 1),
    
    /**
     * Prioridad media.
     */
    MEDIUM("Media", 2),
    
    /**
     * Prioridad alta.
     */
    HIGH("Alta", 3),
    
    /**
     * Prioridad crítica.
     */
    CRITICAL("Crítica", 4);
    
    private final String displayName;
    private final int numericValue;
    
    Priority(String displayName, int numericValue) {
        this.displayName = displayName;
        this.numericValue = numericValue;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getNumericValue() {
        return numericValue;
    }
    
    /**
     * Verifica si esta prioridad es mayor que otra.
     */
    public boolean isHigherThan(Priority other) {
        return this.numericValue > other.numericValue;
    }
    
    /**
     * Verifica si esta prioridad es menor que otra.
     */
    public boolean isLowerThan(Priority other) {
        return this.numericValue < other.numericValue;
    }
    
    /**
     * Obtiene la prioridad por su valor numérico.
     */
    public static Priority fromNumericValue(int value) {
        for (Priority priority : values()) {
            if (priority.numericValue == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("No existe prioridad con valor: " + value);
    }
}
