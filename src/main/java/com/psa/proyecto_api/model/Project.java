package com.psa.proyecto_api.model;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
@Getter
@Setter
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
    
    @NotNull(message = "El tipo de proyecto es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProjectType type;

    @NotNull(message = "El tipo de facturación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "billingType", nullable = false)
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
    
    // Relaciones externas
    @NotNull(message = "El cliente es obligatorio")
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "leader_id")
    private Integer leaderId;
    
    // Relaciones internas
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProjectTag> projectTags = new ArrayList<>();
    

    // Constructor
    
    /**
     * Constructor para crear un proyecto con los campos obligatorios.
     */
    public Project(String name, Integer clientId, ProjectType type, ProjectBillingType billingType, LocalDate startDate) {
        this.name = name;
        this.clientId = clientId;
        this.type = type;
        this.billingType = billingType;
        this.startDate = startDate;
        this.status = ProjectStatus.INITIATED;
    }

    // Métodos de gestión de tareas
    
    /**
     * Agrega una tarea al proyecto estableciendo la relación bidireccional.
     */
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        tasks.add(task);
        task.setProject(this);

        // Actualizar las horas estimadas del proyecto al agregar una tarea
        this.estimatedHours = getTotalEstimatedHoursFromTasks();
    }
    
    /**
     * Remueve una tarea del proyecto.
     */
    public void removeTask(Task task) {
        if (task == null) {
            return;
        }
        tasks.remove(task);
        task.setProject(null);

        // Actualizar las horas estimadas del proyecto al agregar una tarea
        this.estimatedHours = getTotalEstimatedHoursFromTasks();
    }
    
    // Métodos de gestión de tags
    
    /**
     * Agrega un tag al proyecto evitando duplicados.
     */
    public void addTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return;
        }
        
        String normalizedTagName = tagName.trim();
        
        // Verificar si el tag ya existe
        boolean tagExists = projectTags.stream()
            .anyMatch(projectTag -> projectTag.hasTagName(normalizedTagName));
            
        if (!tagExists) {
            ProjectTag newTag = ProjectTag.builder()
                .tagName(normalizedTagName)
                .project(this)
                .build();
            
            projectTags.add(newTag);
        }
    }
    
    /**
     * Remueve un tag del proyecto.
     */
    public void removeTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return;
        }
        
        String normalizedTagName = tagName.trim();
        
        projectTags.removeIf(projectTag -> projectTag.hasTagName(normalizedTagName));
    }
    
    // Métodos de consulta de estado
    
    /**
     * Verifica si el proyecto está iniciado.
     */
    public boolean isInitiated() {
        return this.status.isInitiated();
    }

    /**
     * Verifica si el proyecto está activo (no completado).
     */
    public boolean isActive() {
        return this.status.isActive();
    }
    
    /**
     * Verifica si el proyecto está finalizado.
     */
    public boolean isFinished() {
        return this.status.isTransition();
    }
    
    // Métodos de cálculo
    
    /**
     * Calcula el total de horas estimadas en todas las tareas del proyecto.
     */
    public int getTotalEstimatedHoursFromTasks() {
        return tasks.stream()
            .mapToInt(Task::getEstimatedHoursOrZero)
            .sum();
    }
    
    /**
     * Obtiene el porcentaje de progreso del proyecto basado en las tareas completadas.
     */
    public double getProgressPercentage() {
        if (tasks.isEmpty()) {
            return 0.0;
        }
        
        long completedTasks = tasks.stream()
            .mapToLong(Task::isFinishedAsLong)
            .sum();
            
        return (double) completedTasks / tasks.size() * 100.0;
    }
    
    /**
     * Obtiene la cantidad de tareas activas en el proyecto.
     */
    public long getActiveTasksCount() {
        return tasks.stream()
            .mapToLong(Task::isActiveAsLong)
            .sum();
    }
    
    /**
     * Obtiene la cantidad de tareas completadas en el proyecto.
     */
    public long getCompletedTasksCount() {
        return tasks.stream()
            .mapToLong(Task::isFinishedAsLong)
            .sum();
    }
    
    /**
     * Obtiene las tareas no asignadas del proyecto.
     */
    public List<Task> getUnassignedTasks() {
        return tasks.stream()
            .filter(task -> !task.isAssigned())
            .toList();
    }
    
    /**
     * Verifica si el proyecto tiene un tag específico.
     */
    public boolean hasTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return false;
        }
        
        String normalizedTagName = tagName.trim();
        return projectTags.stream()
            .anyMatch(projectTag -> projectTag.hasTagName(normalizedTagName));
    }
    
    /**
     * Obtiene todos los nombres de tags del proyecto.
     */
    public List<String> getTagNames() {
        return projectTags.stream()
            .map(ProjectTag::getTagName)
            .sorted()
            .toList();
    }
    
    /**
     * Busca una tarea por su ID.
     */
    public Optional<Task> findTaskById(Long taskId) {
        if (taskId == null) {
            return Optional.empty();
        }
        
        return tasks.stream()
            .filter(task -> task.hasId(taskId))
            .findFirst();
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