package com.psa.proyecto_api.repository;

import com.psa.proyecto_api.model.Project;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // Consultas básicas por campos simples
    List<Project> findAll();
    List<Project> findByClientId(Integer clientId);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByLeaderId(Integer leaderId);
    List<Project> findByType(ProjectType type);
    
    // Consulta por rango de fechas
    @Query("SELECT p FROM Project p WHERE p.startDate BETWEEN :startDate AND :endDate")
    List<Project> findByDateRange(@Param("startDate") LocalDate startDate, 
                                  @Param("endDate") LocalDate endDate);
    
    // Consultas combinadas para filtros más específicos
    List<Project> findByClientIdAndStatus(Integer clientId, ProjectStatus status);
    List<Project> findByTypeAndStatus(ProjectType type, ProjectStatus status);
    List<Project> findByLeaderIdAndStatus(Integer leaderId, ProjectStatus status);
    
    // Consulta para proyectos activos (no finalizados ni cancelados)
    @Query("SELECT p FROM Project p WHERE p.status NOT IN ('INITIATED', 'TRANSITION')")
    List<Project> findActiveProjects();
    
    // Consulta para proyectos por cliente y tipo
    List<Project> findByClientIdAndType(Integer clientId, ProjectType type);
    
    // Consulta para contar proyectos por estado
    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = :status")
    Long countByStatus(@Param("status") ProjectStatus status);
    
    // Consulta para proyectos que terminan en un rango de fechas (para alertas)
    @Query("SELECT p FROM Project p WHERE p.endDate BETWEEN :startDate AND :endDate")
    List<Project> findProjectsEndingInDateRange(@Param("startDate") LocalDate startDate, 
                                                @Param("endDate") LocalDate endDate);
    
    // Verificar si existe un proyecto con el mismo nombre para un cliente
    boolean existsByClientIdAndName(Integer clientId, String name);
    
    // Buscar por nombre (para búsquedas)
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Project> findByNameContainingIgnoreCase(@Param("name") String name);

    // Consultas por etiquetas
    @Query("SELECT p FROM Project p JOIN p.projectTags tag WHERE tag.tagName = :tagName")
    List<Project> findByTagName(@Param("tagName") String tagName);
    
    @Query("SELECT p FROM Project p JOIN p.projectTags tag WHERE tag.tagName IN :tagNames")
    List<Project> findByTagNames(@Param("tagNames") List<String> tagNames);
}
