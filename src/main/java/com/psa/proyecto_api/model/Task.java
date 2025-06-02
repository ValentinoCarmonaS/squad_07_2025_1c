package com.psa.proyecto_api.model;

import com.psa.proyecto_api.model.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa una tarea dentro de un proyecto.
 * 
 * Una tarea pertenece a un proyecto y puede tener tickets y tags asociados.
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
@ToString(exclude = {"project", "taskTags", "taskTickets"})
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private TaskStatus status = TaskStatus.TO_DO;
    
    @Positive(message = "Las horas estimadas deben ser positivas")
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    
    @Column(name = "assigned_resource_id")
    private Integer assignedResourceId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "due_date")
    private LocalDate dueDate;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TaskTag> taskTags = new ArrayList<>();
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TaskTicket> taskTickets = new ArrayList<>();
    
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
    
    // Métodos de gestión de tickets
    
    /**
     * Asocia un ticket con la tarea evitando duplicados.
     */
    public void addTicket(Integer ticketId) {
        if (ticketId == null) {
            return;
        }
        
        // Verificar si el ticket ya está asociado
        boolean ticketExists = taskTickets.stream()
            .anyMatch(taskTicket -> taskTicket.belongsToTicket(ticketId));
            
        if (!ticketExists) {
            TaskTicket newTaskTicket = TaskTicket.builder()
                .ticketId(ticketId)
                .task(this)
                .build();
            
            taskTickets.add(newTaskTicket);
        }
    }
    
    /**
     * Remueve la asociación con un ticket.
     */
    public void removeTicket(Integer ticketId) {
        if (ticketId == null) {
            return;
        }
        
        taskTickets.removeIf(taskTicket -> taskTicket.belongsToTicket(ticketId));
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
     * Verifica si la tarea está atrasada (fecha vencida y no completada).
     */
    public boolean isOverdue() {
        if (dueDate == null || isFinished()) {
            return false;
        }
        
        return LocalDate.now().isAfter(dueDate);
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
    
    // Métodos auxiliares para cálculos (Tell, don't ask)
    
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
     * Verifica si la tarea está asociada a un ticket específico.
     */
    public boolean hasTicket(Integer ticketId) {
        if (ticketId == null) {
            return false;
        }
        
        return taskTickets.stream()
            .anyMatch(taskTicket -> taskTicket.belongsToTicket(ticketId));
    }
    
    /**
     * Obtiene todos los IDs de tickets asociados a la tarea.
     */
    public List<Integer> getTicketIds() {
        return taskTickets.stream()
            .map(TaskTicket::getTicketId)
            .sorted()
            .toList();
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