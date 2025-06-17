package com.psa.proyecto_api.steps;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;

import com.psa.proyecto_api.builders.ProjectTestDataBuilder;
import com.psa.proyecto_api.builders.TaskTestDataBuilder;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.TaskResponse;
import com.psa.proyecto_api.model.enums.TaskStatus;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
@Rollback
public class CambiarEstadoTareaSteps {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ProjectTestDataBuilder projectBuilder;
    private TaskTestDataBuilder taskBuilder;

    private ProjectResponse project;
    private TaskResponse task;
    private TaskResponse lastResult;
    private Exception lastError;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1";
    }

    private void initializeBuilders() {
        if (projectBuilder == null) {
            String projectApiUrl = getBaseUrl() + "/proyectos";
            projectBuilder = new ProjectTestDataBuilder(projectApiUrl, restTemplate);
        }
    }

    private void ensureProjectExists() {
        initializeBuilders();
        if (project == null) {
            try {
                project = projectBuilder.createDefaultProject();
                if (project == null) {
                    throw new RuntimeException("Project creation returned null");
                }
                
                String taskApiUrl = getBaseUrl() + "/proyectos/" + project.getId() + "/tareas";
                taskBuilder = new TaskTestDataBuilder(taskApiUrl, restTemplate);
                
            } catch (Exception e) {
                throw new RuntimeException("Failed to create test project: " + e.getMessage(), e);
            }
        }
    }

    @Given("existe una tarea con estado {string}")
    public void existeUnaTareaConEstado(String estado) {
        ensureProjectExists();
        
        try {
            TaskStatus targetStatus = TaskStatus.valueOf(estado);
            
            task = taskBuilder.createTaskWithStatus("Tarea de Prueba", 10, targetStatus);
            
            assertNotNull("Failed to create task", task);
            assertEquals("Task status not set correctly", targetStatus, task.getStatus());
            
        } catch (Exception e) {
            fail("Error setting up task with status " + estado + ": " + e.getMessage());
        }
    }

    @When("el usuario intenta iniciar la tarea")
    public void elUsuarioIntentaIniciarLaTarea() {
        lastError = null;
        lastResult = null;
        
        try {
            lastResult = taskBuilder.activate(task);
            if (lastResult != null) {
                task = lastResult;
            }
        } catch (Exception e) {
            lastError = e;
        }
    }

    @When("el usuario intenta completar la tarea")
    public void elUsuarioIntentaCompletarLaTarea() {
        lastError = null;
        lastResult = null;
        
        try {
            if (task.getStatus() == TaskStatus.TO_DO) {
                lastResult = taskBuilder.deactivate(task);
            } else {
                lastResult = taskBuilder.deactivate(task);
            }
            
            if (lastResult != null) {
                task = lastResult;
            }
        } catch (Exception e) {
            lastError = e;
        }
    }

    @When("el usuario intenta retroceder la tarea")
    public void elUsuarioIntentaRetrocederLaTarea() {
        lastError = null;
        lastResult = null;
        
        try {
            lastResult = taskBuilder.setStatus(task, TaskStatus.TO_DO);
            if (lastResult != null) {
                task = lastResult;
            }
        } catch (Exception e) {
            lastError = e;
        }
    }

    @Then("la tarea tiene el estado {string}")
    public void laTareaTieneElEstado(String estado) {
        assertNotNull("La tarea no existe", task);
        TaskStatus expectedStatus = TaskStatus.valueOf(estado);
        assertEquals("El estado de la tarea no coincide", expectedStatus, task.getStatus());
    }

    @Then("se rechaza la operación por estado inválido")
    public void seRechazaLaOperacionPorEstadoInvalido() {
        assertTrue("Se esperaba que la operación fallara", 
            lastResult == null || lastError != null);
    }

    @Then("el estado de la tarea se mantiene en {string}")
    public void elEstadoDeLaTareaSeMantieneEn(String estado) {
        assertNotNull("La tarea no existe", task);
        TaskStatus expectedStatus = TaskStatus.valueOf(estado);
        assertEquals("El estado de la tarea cambió cuando no debería", expectedStatus, task.getStatus());
    }
}