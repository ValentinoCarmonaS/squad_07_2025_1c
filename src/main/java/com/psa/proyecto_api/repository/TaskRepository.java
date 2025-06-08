package com.psa.proyecto_api.repository;

import com.psa.proyecto_api.model.Task;
import com.psa.proyecto_api.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Consultas básicas por proyecto
    List<Task> findAll();
    List<Task> findByProjectId(Long projectId);    
    
    // Consultas por estado
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);
        
    // Consultas por responsable
    List<Task> findByAssignedResourceId(Integer assignedResourceId);
    List<Task> findByAssignedResourceIdAndStatus(Integer assignedResourceId, TaskStatus status);
        
    // Consultas por etiquetas
    @Query("SELECT t FROM Task t JOIN t.taskTags tag WHERE tag.tagName = :tagName")
    List<Task> findByTagName(@Param("tagName") String tagName);
    
    @Query("SELECT t FROM Task t JOIN t.taskTags tag WHERE tag.tagName IN :tagNames")
    List<Task> findByTagNames(@Param("tagNames") List<String> tagNames);
    
    // Consulta para tareas de un proyecto con etiqueta específica
    @Query("SELECT t FROM Task t JOIN t.taskTags tag WHERE t.project.id = :projectId AND tag.tagName = :tagName")
    List<Task> findByProjectIdAndTagName(@Param("projectId") Long projectId, @Param("tagName") String tagName);
    
    // Estadísticas útiles
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.id = :projectId AND t.status = :status")
    Long countByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") TaskStatus status);
    
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedResourceId = :assignedResourceId AND t.status = :status")
    Long countByAssignedResourceIdAndStatus(@Param("assignedResourceId") Integer assignedResourceId, @Param("status") TaskStatus status);
    
    // Tareas sin asignar
    List<Task> findByAssignedResourceIdIsNull();
    List<Task> findByProjectIdAndAssignedResourceIdIsNull(Long projectId);
}
