package com.psa.proyecto_api.model;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;

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
 * Entidad que representa un proyecto en el sistema.
 * 
 * Esta clase encapsula toda la información relacionada con un proyecto,
 * incluyendo sus tareas asociadas y tags.
 */
@Entity
@Table(name = "projects", indexes = {
    @Index(name = "idx_projects_client_id", columnList = "client_id"),
    @Index(name = "idx_projects_type", columnList = "type"),
    @Index(name = "idx_projects_status", columnList = "status"),
    @Index(name = "idx_projects_leader_id", columnList = "leader_id"),
    @Index(name = "idx_projects_dates", columnList = "start_date, end_date")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"tasks", "projectTags"})
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "client_id")
    private Integer clientId;
    
    @NotNull(message = "El tipo de proyecto es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProjectType type;

    @NotNull(message = "El tipo de facturación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "billing_type", nullable = false)
    private ProjectBillingType billingType;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Positive(message = "Las horas estimadas deben ser positivas")
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private ProjectStatus status = ProjectStatus.INITIATED;
    
    @Column(name = "leader_id")
    private Integer leaderId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Relaciones
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProjectTag> projectTags = new ArrayList<>();
    
    // Métodos
    
    /**
     * Agrega una tarea al proyecto.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    /**
     * Remueve una tarea del proyecto.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    
    /**
     * Agrega un tag al proyecto evitando duplicados.
     */
    public void addTag(String tagName) {
        // Pendiente de implementación
    }
    
    /**
     * Remueve un tag del proyecto.
     */
    public void removeTag(String tagName) {
        // Pendiente de implementación
    }
    
    /**
     * Verifica si el proyecto está iniciado.
     */
    public boolean isInitiated() {
        return this.status.isInitiated();
    }

    /**
     * Verifica si el proyecto está activo (no completado) .
     */
    public boolean isActive() {
        return this.status.isActive();
    }
    
    /**
     * Verifica si el proyecto está en transición.
     */
    public boolean isFinished() {
        return this.status.isTransition();
    }
    
    /**
     * Calcula el total de horas estimadas en todas las tareas del proyecto.
     */
    public int getTotalEstimatedHoursFromTasks() {
        // Pendiente de implementación
    }
    
    /**
     * Verifica si las fechas del proyecto son válidas.
     */
    @AssertTrue(message = "La fecha de fin debe ser posterior o igual a la fecha de inicio")
    private boolean isValidDateRange() {
        return endDate == null || endDate.isAfter(startDate) || endDate.isEqual(startDate);
    }
    
    // equals y hashCode basados en el ID (para entidades JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Objects.equals(id, project.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
