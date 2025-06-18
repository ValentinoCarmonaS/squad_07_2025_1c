package com.psa.proyecto_api.steps;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;

import com.psa.proyecto_api.builders.ProjectTestDataBuilder;
import com.psa.proyecto_api.builders.TaskTestDataBuilder;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.TaskResponse;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.TaskStatus;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

@Transactional
@Rollback
public class MonitorearProgresoProyectoSteps {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ProjectTestDataBuilder projectBuilder;
    private TaskTestDataBuilder taskBuilder;

    private ProjectResponse project;
    private TaskResponse currentTask;
    private Map<String, TaskResponse> namedTasks = new java.util.HashMap<>();

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

    private void refreshProject() {
        try {
            String projectApiUrl = getBaseUrl() + "/proyectos";
            project = projectBuilder.getProject(project.getId());
            assertNotNull("Failed to refresh project", project);
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh project: " + e.getMessage(), e);
        }
    }

    @Given("existe un proyecto con estado {string}")
    public void existeUnProyectoConEstado(String estado) {
        ensureProjectExists();
        
        try {
            ProjectStatus expectedStatus = ProjectStatus.valueOf(estado);
            
            namedTasks.clear();
            currentTask = null;
            
            switch (expectedStatus) {
                case INITIATED:
                    break;
                    
                case IN_PROGRESS:
                    TaskResponse setupTask = taskBuilder.createTaskWithStatus("Setup Task", 5, TaskStatus.IN_PROGRESS);
                    assertNotNull("Failed to create setup task", setupTask);
                    namedTasks.put("Setup Task", setupTask);
                    break;
                    
                case TRANSITION:
                    TaskResponse completedTask = taskBuilder.createTaskWithStatus("Setup Completed Task", 5, TaskStatus.DONE);
                    assertNotNull("Failed to create completed task", completedTask);
                    namedTasks.put("Setup Completed Task", completedTask);
                    break;
                    
                default:
                    throw new IllegalArgumentException("Unsupported project status for test setup: " + expectedStatus);
            }
            
            refreshProject();
            
            assertEquals("Project not in expected state after setup", expectedStatus, project.getStatus());
            
        } catch (Exception e) {
            fail("Error setting up project with status " + estado + ": " + e.getMessage());
        }
    }

    @Given("existe una tarea con el estado {string}")
    public void existeUnaTareaConElEstado(String estado) {
        ensureProjectExists();
        
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(estado);
            currentTask = taskBuilder.createTaskWithStatus("Tarea de Prueba", 10, taskStatus);
            
            assertNotNull("Failed to create task", currentTask);
            assertEquals("Task status not set correctly", taskStatus, currentTask.getStatus());
            
        } catch (Exception e) {
            fail("Error creating task with status " + estado + ": " + e.getMessage());
        }
    }

    @Given("existen tareas con los siguientes estados:")
    public void existenTareasConLosSiguientesEstados(DataTable dataTable) {
        ensureProjectExists();
        namedTasks.clear();
        
        try {
            List<Map<String, String>> taskData = dataTable.asMaps(String.class, String.class);
            
            for (Map<String, String> row : taskData) {
                String taskName = row.get("taskName");
                TaskStatus status = TaskStatus.valueOf(row.get("status"));
                
                TaskResponse task = taskBuilder.createTaskWithStatus(taskName, 10, status);
                assertNotNull("Failed to create task: " + taskName, task);
                assertEquals("Task status not set correctly for " + taskName, status, task.getStatus());
                
                namedTasks.put(taskName, task);
            }
            
        } catch (Exception e) {
            fail("Error creating tasks: " + e.getMessage());
        }
    }

    @Given("existe una tarea adicional con estado {string} llamada {string}")
    public void existeUnaTareaAdicionalConEstado(String estado, String taskName) {
        ensureProjectExists();
        
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(estado);
            TaskResponse task = taskBuilder.createTaskWithStatus(taskName, 10, taskStatus);
            
            assertNotNull("Failed to create additional task: " + taskName, task);
            assertEquals("Task status not set correctly for " + taskName, taskStatus, task.getStatus());
            
            namedTasks.put(taskName, task);
            
        } catch (Exception e) {
            fail("Error creating additional task " + taskName + " with status " + estado + ": " + e.getMessage());
        }
    }

    @When("el usuario inicia la tarea")
    public void elUsuarioIniciaLaTarea() {
        try {
            assertNotNull("No task available to start", currentTask);
            currentTask = taskBuilder.activate(currentTask);
            assertNotNull("Task activation failed", currentTask);
            
        } catch (Exception e) {
            fail("Error starting task: " + e.getMessage());
        }
    }

    @When("el usuario inicia la tarea {string}")
    public void elUsuarioIniciaLaTarea(String taskName) {
        try {
            TaskResponse task = namedTasks.get(taskName);
            assertNotNull("Task not found: " + taskName, task);
            
            TaskResponse updatedTask = taskBuilder.activate(task);
            assertNotNull("Task activation failed for: " + taskName, updatedTask);
            
            namedTasks.put(taskName, updatedTask);
            
        } catch (Exception e) {
            fail("Error starting task " + taskName + ": " + e.getMessage());
        }
    }

    @When("el usuario completa la tarea")
    public void elUsuarioCompletaLaTarea() {
        try {
            assertNotNull("No task available to complete", currentTask);
            currentTask = taskBuilder.deactivate(currentTask);
            assertNotNull("Task completion failed", currentTask);
            
        } catch (Exception e) {
            fail("Error completing task: " + e.getMessage());
        }
    }

    @When("el usuario completa la tarea {string}")
    public void elUsuarioCompletaLaTarea(String taskName) {
        try {
            TaskResponse task = namedTasks.get(taskName);
            assertNotNull("Task not found: " + taskName, task);
            
            TaskResponse updatedTask = taskBuilder.deactivate(task);
            assertNotNull("Task completion failed for: " + taskName, updatedTask);
            
            namedTasks.put(taskName, updatedTask);
            
            currentTask = updatedTask;
            
        } catch (Exception e) {
            fail("Error completing task " + taskName + ": " + e.getMessage());
        }
    }

    @Then("la tarea cambia a estado {string}")
    public void laTareaCambiaAEstado(String estado) {
        assertNotNull("No current task to check", currentTask);
        TaskStatus expectedStatus = TaskStatus.valueOf(estado);
        assertEquals("Task status does not match", expectedStatus, currentTask.getStatus());
    }

    @Then("la tarea {string} cambia a estado {string}")
    public void laTareaCambiaAEstado(String taskName, String estado) {
        TaskResponse task = namedTasks.get(taskName);
        assertNotNull("Task not found: " + taskName, task);
        
        TaskStatus expectedStatus = TaskStatus.valueOf(estado);
        assertEquals("Task status does not match for " + taskName, expectedStatus, task.getStatus());
    }

    @And("el proyecto actualiza su estado a {string}")
    public void elProyectoActualizaSuEstadoA(String estado) {
        try {
            refreshProject();
            
            ProjectStatus expectedStatus = ProjectStatus.valueOf(estado);
            assertEquals("Project status was not updated correctly", expectedStatus, project.getStatus());
            
        } catch (Exception e) {
            fail("Error checking project status: " + e.getMessage());
        }
    }

    @Then("el proyecto debe tener estado {string}")
    public void elProyectoDebeTenerEstado(String estado) {
        try {
            refreshProject();
            
            ProjectStatus expectedStatus = ProjectStatus.valueOf(estado);
            assertEquals("Project status does not match expected", expectedStatus, project.getStatus());
            
        } catch (Exception e) {
            fail("Error checking project status: " + e.getMessage());
        }
    }

    @Then("no existen tareas para el proyecto")
    public void noExistenTareasParaElProyecto() {
        try {
            refreshProject();
            assertEquals("Project should have no tasks", 0, project.getTasks().size());
        } catch (Exception e) {
            fail("Error checking project tasks: " + e.getMessage());
        }
    }
}