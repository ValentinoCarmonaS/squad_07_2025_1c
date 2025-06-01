package com.psa.proyecto_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que representa la relación entre una tarea y un ticket del módulo de soporte.
 * 
 * Esta entidad actúa como tabla de unión entre tareas y tickets externos,
 * permitiendo que una tarea pueda estar asociada a múltiples tickets.
 */
@Entity
@Table(name = "task_tickets", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"task_id", "ticket_id"}),
       indexes = {
           @Index(name = "idx_task_tickets_task_id", columnList = "task_id"),
           @Index(name = "idx_task_tickets_ticket_id", columnList = "ticket_id")
       })
@Getter // Revisar si es necesario
@Setter // Revisar si es necesario
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "task")
public class TaskTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    @NotNull(message = "El ID del ticket es obligatorio")
    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Verifica si este TaskTicket corresponde al ticket especificado.
     */
    public boolean belongsToTicket(Integer ticketId) {
        return this.ticketId != null && this.ticketId.equals(ticketId);
    }
    
    /**
     * Verifica si este TaskTicket pertenece a la tarea especificada.
     */
    public boolean belongsToTask(Long taskId) {
        return this.task != null && this.task.hasId(taskId);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskTicket that)) return false;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
