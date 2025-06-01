package com.psa.proyecto_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que representa un tag asociado a una tarea.
 */
@Entity
@Table(name = "task_tags", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"task_id", "tag_name"}),
       indexes = {
           @Index(name = "idx_task_tags_task_id", columnList = "task_id"),
           @Index(name = "idx_task_tags_name", columnList = "tag_name")
       })
@Getter // Revisar si es necesario
@Setter // Revisar si es necesario
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "task")
public class TaskTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del tag es obligatorio")
    @Size(max = 50, message = "El nombre del tag no puede exceder los 50 caracteres")
    @Column(name = "tag_name", nullable = false)
    private String tagName;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Métodos
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskTag taskTag)) return false;
        return Objects.equals(id, taskTag.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}