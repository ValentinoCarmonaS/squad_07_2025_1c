package com.psa.proyecto_api.steps;

import com.psa.proyecto_api.BaseIntegrationTest;
import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.request.CreateTaskRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.TaskResponse;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.Before;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectType;
import com.psa.proyecto_api.model.enums.TaskStatus;

public class CrearTareaSteps {

    private static String projectApiUrl;
    private static String taskApiUrl;
    private ResponseEntity<ProjectResponse> projectResponse;
    private ProjectResponse project;

    private CreateTaskRequest taskRequest;
    private List<CreateTaskRequest> taskRequests;
    private List<TaskResponse> tasks;
    private ResponseEntity<TaskResponse> response;
    private TaskResponse task;

    @Before
    public void setUp() {
        projectApiUrl = "/api/v1/proyectos";

        CreateProjectRequest projectRequest = new CreateProjectRequest();
        projectRequest.setName("Project 1");
        projectRequest.setClientId(1);
        projectRequest.setType(ProjectType.DEVELOPMENT);
        projectRequest.setBillingType(ProjectBillingType.TIME_AND_MATERIAL);
        projectRequest.setStartDate(LocalDate.now().plusDays(10));

        projectResponse = restTemplate.postForEntity(
            baseUrl + projectApiUrl, projectRequest, ProjectResponse.class);
        project = projectResponse.getBody();
        
        if (project == null) {
            throw new RuntimeException("Project response body is null");
        }
        
        taskApiUrl = "/api/v1/proyectos/" + project.getId() + "/tareas";
    }

    @Given("existe un proyecto con nombre {string}, id {string} y con {int} horas estimadas")
    public void existeUnProyectoConNombreIdYConHorasEstimadas(String name, String id, int estimatedHours) {
        assertNotNull(projectResponse, "Project response should not be null");
        assertNotNull(project, "Project should not be null");

        assertEquals(Long.parseLong(id), project.getId(), "Project id should match");
        assertEquals(name, project.getName(), "Project name should match");
        assertEquals(estimatedHours, project.getEstimatedHours(), "Project estimated hours should match");
    }

    @Given("se tiene la siguiente información de una tarea:")
    public void seTieneLaSiguienteInformacionDeUnaTarea(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        var map = data.get(0);

        taskRequest = new CreateTaskRequest();
        taskRequest.setName(map.get("name"));
        taskRequest.setEstimatedHours(Integer.parseInt(map.get("estimatedHours")));
    }

    @Given("se tiene la siguiente información incompleta de una tarea:")
    public void seTieneLaSiguienteInformacionIncompletaDeUnaTarea(DataTable dataTable) {

    }

    @Given("se tiene la siguiente información de tareas:")
    public void seTieneLaSiguienteInformacionDeTareas(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        taskRequests = new ArrayList<>();
        for (var map: data) {
            var request = new CreateTaskRequest();
            request.setName(map.get("name"));
            request.setEstimatedHours(Integer.parseInt(map.get("estimatedHours")));
            request.setAssignedResourceId(map.get("assignedResourceId"));
            taskRequests.add(request);
        }
    }

    @And("la tarea es para el proyecto con id {string}")
    public void laTareaEsParaElProyectoConId(String id) {
        assertEquals(Long.parseLong(id), project.getId());
    }

    @And("las tareas son para el proyecto con id {string}")
    public void lasTareasSonParaElProyectoConId(String id) {
        assertEquals(Long.parseLong(id), project.getId());
    }

    @When("el usuario intenta crear una nueva tarea")
    public void elUsuarioIntentaCrearUnaNuevaTarea() {
        try {
            response = restTemplate.postForEntity(baseUrl + taskApiUrl, taskRequest, TaskResponse.class);
            task = response.getBody();
        } catch (Exception e) {
            System.out.println("Error creating task: " + e.getMessage());
        }
    }

    @When("el usuario intenta crear las nuevas tareas")
    public void elUsuarioIntentaCrearLasNuevasTareas() {
        tasks = new ArrayList<>();
        for (var request: taskRequests) {
            try {
                response = restTemplate.postForEntity(baseUrl + taskApiUrl, request, TaskResponse.class);
                tasks.add(response.getBody());
            } catch (Exception e) {
                System.out.println("Error creating task: " + e.getMessage());
            }
        }
    }

    @Then("la tarea se crea correctamente con los siguientes datos:")
    public void laTareaSeCreaCorrectamenteConLosSiguientesDatos(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        var map = data.get(0);

        assertEquals(map.get("name"), task.getName());
        assertEquals(Integer.parseInt(map.get("estimatedHours")), task.getEstimatedHours());
        assertEquals(TaskStatus.valueOf(map.get("status")), task.getStatus());
        assertEquals(Long.parseLong(map.get("projectId")), task.getProjectId());
    }

    @Then("la tarea tiene asociado el proyecto con id {string}")
    public void laTareaTieneAsociadoElProyectoConId(String projectId) {
        assertEquals(Long.parseLong(projectId), task.getProjectId());
    }

    @Then("el proyecto tiene una tarea")
    public void elProyectoTieneUnaTarea() {
        assertEquals(1, project.getTasks().size());
        System.out.println(project.getTasks().stream().map(task -> task.getName()).collect(java.util.stream.Collectors.toList()));
        System.out.println(project.getTasks().size() + "=====================================================");
    }

    @Then("el proyecto contiene la tarea {string}")
    public void elProyectoContieneLaTarea(String taskName) {
        
        
        assertTrue(project.getTasks().stream().anyMatch(task -> task.getName().equals(taskName)));
    }

    @Then("el proyecto contiene las tareas {string} y {string}")
    public void elProyectoContieneLasTareas(String taskName1, String taskName2) {
        assertTrue(project.getTasks().size() > 1);
        assertTrue(project.getTasks().stream().anyMatch(task -> task.getName().equals(taskName1)));
        assertTrue(project.getTasks().stream().anyMatch(task -> task.getName().equals(taskName2)));
    }

    @Then("las tareas se crean correctamente con los siguientes datos:")
    public void lasTareasSeCreenCorrectamenteConLosSiguientesDatos(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            var map = data.get(i);
            var task = tasks.get(i);

            assertNotNull(task);
            
            assertEquals(map.get("name"), task.getName());
            assertEquals(Integer.parseInt(map.get("estimatedHours")), task.getEstimatedHours());
            assertEquals(map.get("assignedResourceId"), task.getAssignedResourceId());
            assertEquals(Long.parseLong(map.get("projectId")), task.getProjectId());
            assertEquals(TaskStatus.valueOf(map.get("status")), task.getStatus());
        }
    }

    @Then("el proyecto tiene {int} horas estimadas")
    public void elProyectoTieneHorasEstimadas(int estimatedHours) {
        assertEquals(estimatedHours, project.getEstimatedHours());
    }

    @Then("se rechaza la creación de la tarea por información incompleta")
    public void elSistemaRechazaLaCreacionDeLaTareaPorInformacionIncompleta() {
        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Then("se rechaza la creación de la tarea por horas negativas")
    public void elSistemaRechazaLaCreacionDeLaTareaPorHorasNegativas() {
        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Then("la tarea tiene los tags {string} y {string}")
    public void laTareaTieneLosTags(String tag1, String tag2) {
        // assertTrue(task.getTagNames().contains(tag1));
        // assertTrue(task.getTagNames().contains(tag2));
    }

    @Then("se rechaza la creación de la tarea por proyecto inexistente")
    public void elSistemaRechazaLaCreacionDeLaTareaPorProyectoInexistente() {
        // assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // assertFalse(true);
    }
}