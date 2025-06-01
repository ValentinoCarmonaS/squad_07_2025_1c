package com.psa.proyecto_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que representa un tag asociado a un proyecto.
 */
@Entity
@Table(name = "project_tags", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "tag_name"}),
       indexes = {
           @Index(name = "idx_project_tags_project_id", columnList = "project_id"),
           @Index(name = "idx_project_tags_name", columnList = "tag_name")
       })
@Getter // Revisar si es necesario
@Setter // Revisar si es necesario
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "project")
public class ProjectTag {
    
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
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    // Metodos

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTag that)) return false;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
