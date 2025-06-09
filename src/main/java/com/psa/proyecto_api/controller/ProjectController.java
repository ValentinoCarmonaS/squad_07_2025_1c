package com.psa.proyecto_api.controller;

import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.request.ProjectFilterRequest;
import com.psa.proyecto_api.dto.request.UpdateProjectRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.ProjectSummaryResponse;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import com.psa.proyecto_api.service.ProjectService;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // POST /proyectos - Crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // PUT /proyectos/{id} - Modificar información de un proyecto
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateProjectRequest request) {
        ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE /proyectos/{id} - Eliminar un proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // GET /proyectos - Listar todos los proyectos con filtros
    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getProjects(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) ProjectType type,
            @RequestParam(required = false) String tag) {
        
        ProjectFilterRequest filterRequest = ProjectFilterRequest.builder()
            .status(status)
            .type(type)
            .tag(tag)
            .build();
            
        List<ProjectSummaryResponse> filteredProjects = projectService.getProjects(filterRequest);
        return ResponseEntity.ok(filteredProjects);
    }

    // GET /proyectos/{id} - Ver detalles de un proyecto específico
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    // POST /proyectos/{id}/tags - Agregar tag a un proyecto
    @PostMapping("/{id}/tags")
    public ResponseEntity<ProjectResponse> addTagToProject(
            @PathVariable Long id, 
            @RequestBody String tagName) {
        ProjectResponse response = projectService.addTagToProject(id, tagName);
        return ResponseEntity.ok(response);
    }

    // DELETE /proyectos/{id}/tags - Quitar tag de un proyecto
    @DeleteMapping("/{id}/tags")
    public ResponseEntity<ProjectResponse> removeTagFromProject(
            @PathVariable Long id, 
            @RequestParam String tagName) {
        ProjectResponse response = projectService.removeTagFromProject(id, tagName);
        return ResponseEntity.ok(response);
    }

    // PUT /proyectos/{id}/tags - Actualizar tag de un proyecto
    @PutMapping("/{id}/tags")
    public ResponseEntity<ProjectResponse> updateProjectTag(
            @PathVariable Long id, 
            @RequestParam String oldTagName,
            @RequestBody String newTagName) {
        ProjectResponse response = projectService.updateProjectTag(id, oldTagName, newTagName);
        return ResponseEntity.ok(response);
    }

    // GET /proyectos/{id}/tags - Obtener todos los tags de un proyecto
    @GetMapping("/{id}/tags")
    public ResponseEntity<List<String>> getProjectTags(@PathVariable Long id) {
        List<String> tags = projectService.getProjectTags(id);
        return ResponseEntity.ok(tags);
    }
}
