package com.psa.proyecto_api.builders;

import java.time.LocalDate;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectType;

public class ProjectTestDataBuilder {
    
    private final TestRestTemplate restTemplate;
    private final String baseUrl;
    
    public ProjectTestDataBuilder(String baseUrl, TestRestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }
    
    public ProjectResponse createDefaultProject() {
        return createProject("Proyecto 1", ProjectType.DEVELOPMENT);
    }
    
    public ProjectResponse createProject(String name, ProjectType type) {
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName(name);
        request.setClientId(1);
        request.setType(type);
        request.setBillingType(ProjectBillingType.TIME_AND_MATERIAL);
        request.setStartDate(LocalDate.now().plusDays(1));

        try {
            ResponseEntity<ProjectResponse> response = restTemplate.postForEntity(
                baseUrl, request, ProjectResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Project creation failed with status: " + response.getStatusCode());
            }
            
            ProjectResponse project = response.getBody();
            if (project == null) {
                throw new RuntimeException("Project creation returned null");
            }
            
            return project;
        } catch (Exception e) {
            throw new RuntimeException("Error creating project: " + name, e);
        }
    }

    /**
     * Retrieves a project by ID to get updated state
     */
    public ProjectResponse getProject(Long id) {
        try {
            ResponseEntity<ProjectResponse> response = restTemplate.getForEntity(
                baseUrl + "/" + id, ProjectResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to get project with status: " + response.getStatusCode());
            }
            
            ProjectResponse project = response.getBody();
            if (project == null) {
                throw new RuntimeException("Project retrieval returned null");
            }
            
            return project;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving project with ID: " + id, e);
        }
    }
    
    /**
     * Creates a project with specific status (for test setup)
     * Note: This might require additional API endpoints or test-specific methods
     */
    public ProjectResponse createProjectWithStatus(String name, ProjectType type, String status) {
        // For now, create a regular project
        // In a real scenario, you might need special test endpoints or data manipulation
        ProjectResponse project = createProject(name, type);
        
        // Future enhancement: manipulate project status if needed
        // This might require special test endpoints or direct database manipulation
        
        return project;
    }
}