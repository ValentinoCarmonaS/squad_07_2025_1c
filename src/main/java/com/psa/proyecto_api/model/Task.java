package com.psa.proyecto_api.model;

import com.psa.proyecto_api.model.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa una tarea dentro de un proyecto.
 * 
 * Una tarea pertenece a un proyecto y tiene tags asociados.
 */
@Entity
@Table(name = "tasks", indexes = {
    @Index(name = "idx_tasks_project_id", columnList = "project_id"),
    @Index(name = "idx_tasks_status", columnList = "status"),
    @Index(name = "idx_tasks_assigned_resource", columnList = "assigned_resource_id"),
    @Index(name = "idx_tasks_due_date", columnList = "due_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"project", "taskTags"}) // Agregar "taskTickets" si realmente son necesarios los tickets
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private TaskStatus status = TaskStatus.TO_DO;
    
    @Min(value = 0, message = "Las horas estimadas no pueden ser negativas")
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    
    // Relaciones externas
    @Column(name = "assigned_resource_id")
    private Integer assignedResourceId;
    
    // Relaciones internas
    @NotNull(message = "El proyecto es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TaskTag> taskTags = new ArrayList<>();
    
    // Constructor
    public Task(String name, Project project) {
        this.name = name;
        this.project = project;
        this.status = TaskStatus.TO_DO;
    }

    // Metodos de gestion de proyecto
    /**
     * Establece el proyecto al que pertenece la tarea
     */
    public void setProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("El proyecto no puede ser nulo");
        }
        this.project = project;
    }

    // Metodos de gestion de horas
    
    /**
     * Establece las horas estimadas de la tarea.
     */
    public void setEstimatedHours(Integer estimatedHours) {
        if (estimatedHours != null && estimatedHours < 0) {
            throw new IllegalArgumentException("Las horas estimadas no pueden ser negativas");
        }
        this.estimatedHours = estimatedHours;
    }

    // Métodos de gestión de tags
    
    /**
     * Agrega un tag a la tarea evitando duplicados.
     */
    public void addTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return;
        }
        String normalizedTagName = tagName.trim();
        
        // Verificar si el tag ya existe
        boolean tagExists = taskTags.stream()
            .anyMatch(taskTag -> taskTag.hasTagName(normalizedTagName));
            
        if (!tagExists) {
            TaskTag newTag = TaskTag.builder()
                .tagName(normalizedTagName)
                .task(this)
                .build();
            
            taskTags.add(newTag);
        }
    }
    
    /**
     * Remueve un tag de la tarea.
     */
    public void removeTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return;
        }
        String normalizedTagName = tagName.trim();
        taskTags.removeIf(taskTag -> taskTag.hasTagName(normalizedTagName));
    }
    
    // Metodos de gestion de recursos
    /**
     * Asigna un recurso a la tarea.
     */
    public void assignResource(Integer resourceId) {
        if (resourceId != null && resourceId < 0) {
            throw new IllegalArgumentException("El ID del recurso no puede ser negativo");
        }
        this.assignedResourceId = resourceId;
    }

    // Métodos de consulta de estado
    
    /**
     * Verifica si la tarea está pendiente por hacer.
     */
    public boolean isToDo() {
        return status == TaskStatus.TO_DO;
    }

    /**
     * Verifica si la tarea está activa (no completada ni cancelada).
     */
    public boolean isActive() {
        return status == TaskStatus.IN_PROGRESS;
    }
    
    /**
     * Verifica si la tarea ha finalizado.
     */
    public boolean isFinished() {
        return status == TaskStatus.DONE;
    }
    
    /**
     * Verifica si la tarea está asignada a un recurso.
     */
    public boolean isAssigned() {
        return assignedResourceId != null;
    }

    /**
     * Verifica si el id de la tarea es igual al id proporcionado.
     */
    public boolean hasId(Long taskId) {
        return this.id != null && this.id.equals(taskId);
    }
    
    // Métodos auxiliares para cálculos
    
    /**
     * Obtiene las horas estimadas o cero si es null.
     */
    public int getEstimatedHoursOrZero() {
        return estimatedHours != null ? estimatedHours : 0;
    }
    
    /**
     * Retorna 1 si la tarea está finalizada, 0 en caso contrario.
     * Útil para operaciones de suma en streams.
     */
    public long isFinishedAsLong() {
        return isFinished() ? 1L : 0L;
    }
    
    /**
     * Retorna 1 si la tarea está activa, 0 en caso contrario.
     * Útil para operaciones de suma en streams.
     */
    public long isActiveAsLong() {
        return isActive() ? 1L : 0L;
    }
    
    /**
     * Verifica si la tarea tiene un tag específico.
     */
    public boolean hasTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return false;
        }
        
        String normalizedTagName = tagName.trim();
        return taskTags.stream()
            .anyMatch(taskTag -> taskTag.hasTagName(normalizedTagName));
    }
    
    /**
     * Obtiene todos los nombres de tags de la tarea.
     */
    public List<String> getTagNames() {
        return taskTags.stream()
            .map(TaskTag::getTagName)
            .sorted()
            .toList();
    }
    
    // Métodos de transición de estado
    
    /**
     * Inicia la tarea cambiando su estado a IN_PROGRESS.
     */
    public void start() {
        if (status == TaskStatus.TO_DO) {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }
    
    /**
     * Completa la tarea cambiando su estado a DONE.
     */
    public void complete() {
        if (status == TaskStatus.IN_PROGRESS) {
            this.status = TaskStatus.DONE;
        }
    }
    
    // equals y hashCode basados en el ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}