package com.psa.proyecto_api.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

import com.psa.proyecto_api.steps.BaseCucumber;

public class AsignarRecursoTareaSteps extends BaseCucumber {
    
    @Before
    public void setUp() {
        super.setUp();
    }

    @Given("existe la siguiente tarea sin recurso asignado")
    public void existeLaSiguienteTareaSinRecursoAsignado() {
        projectBuilder.createBasicProject();
        taskBuilder.clearTasks();
        project = projectBuilder.getProject();
        assertTrue("La lista de tareas debería estar vacía", project.getTasks().isEmpty());

        taskBuilder.createBasicTask(project.getId());
        task = taskBuilder.getTask();
        assertNotNull("La tarea debería existir", task);
        
        project = projectBuilder.getProject();
    }

    @When("se solicita la asignación del recurso {string} a la tarea")
    public void seSolicitaLaAsignacionDeUnRecursoATarea(String resourceId) {
        taskBuilder.assignResourceToTask(task.getId(), resourceId);
    }

    @Then("el recurso se asigna correctamente a la tarea")
    public void elRecursoSeAsignaCorrectamenteATarea() {
        task = taskBuilder.getTask();
        assertNotNull("La tarea debería existir", task);
    }

    @Given("existe la siguiente tarea con recurso asignado")
    public void existeLaSiguienteTareaConRecursoAsignado() {
        projectBuilder.createBasicProject();
        taskBuilder.clearTasks();
        project = projectBuilder.getProject();
        assertTrue("La lista de tareas debería estar vacía", project.getTasks().isEmpty());

        taskBuilder.createTaskWithAssignedResource(project.getId(), "124e4567-e89b-12d3-a456-426614174000");
        task = taskBuilder.getTask();
        assertNotNull("La tarea debería existir", task);
        assertEquals("El recurso debería estar asignado", "124e4567-e89b-12d3-a456-426614174000", task.getAssignedResourceId());
        
        project = projectBuilder.getProject();
    }

    
}
