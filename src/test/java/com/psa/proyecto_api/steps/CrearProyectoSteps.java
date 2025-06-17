package com.psa.proyecto_api.steps;

import java.io.Console;

import com.psa.proyecto_api.dto.request.CreateProjectRequest;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.model.enums.ProjectBillingType;
import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import org.springdoc.core.service.GenericResponseService;

import com.psa.proyecto_api.dto.response.ProjectSummaryResponse;

public class CrearProyectoSteps {

    @LocalServerPort
    private int port;

    private String baseUrl;
    private final String apiUrl = "/api/v1/proyectos";

    @Autowired
    private TestRestTemplate restTemplate;

    private CreateProjectRequest projectRequest;
    private ResponseEntity<ProjectResponse> response;
    private ProjectResponse project;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Given("se tiene la siguiente información del proyecto:")
    public void seTieneLaSiguienteInformacionDelProyecto(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        var map = data.get(0);
        
        projectRequest = new CreateProjectRequest();
        projectRequest.setName(map.get("name"));
        projectRequest.setClientId(Integer.parseInt(map.get("clientId")));
        projectRequest.setType(ProjectType.valueOf(map.get("type")));
        projectRequest.setBillingType(ProjectBillingType.valueOf(map.get("billingType")));
        projectRequest.setStartDate(LocalDate.parse(map.get("startDate")));

        if (map.get("endDate") != null) {
            projectRequest.setEndDate(LocalDate.parse(map.get("endDate")));
        }
        if (map.get("leaderId") != null) {
            projectRequest.setLeaderId(map.get("leaderId"));
        }    
    }

    @Given("se tiene la siguiente información incompleta del proyecto:")
    public void seTieneLaSiguienteInformacionIncompletaDelProyecto(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        var map = data.get(0);
        
        projectRequest = new CreateProjectRequest();
        if (map.get("name") != null) {
            projectRequest.setName(map.get("name"));
        }
        if (map.get("clientId") != null) {
            projectRequest.setClientId(Integer.parseInt(map.get("clientId")));
        }
        if (map.get("type") != null) {
            projectRequest.setType(ProjectType.valueOf(map.get("type")));
        }
        if (map.get("billingType") != null) {
            projectRequest.setBillingType(ProjectBillingType.valueOf(map.get("billingType")));
        }
        if (map.get("startDate") != null) {
            projectRequest.setStartDate(LocalDate.parse(map.get("startDate")));
        }
    }

    @When("se envía la solicitud para crear el proyecto")
    public void seEnviaLaSolicitudParaCrearElProyecto() {
        try {
            response = restTemplate.postForEntity(
                baseUrl + apiUrl, projectRequest, ProjectResponse.class);
            project = response.getBody();
        } catch (Exception e) {
            System.out.println("Error creating project: " + e.getMessage());
        }
    }

    @Then("el proyecto debería ser creado exitosamente con los siguientes datos:")
    public void elProyectoDeberiaSerCreadoExitosamenteConLosSiguientesDatos(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        var map = data.get(0);

        assertNotNull(project, "Project should not be null");
        
        assertEquals(map.get("name"), project.getName());
        assertEquals(Integer.parseInt(map.get("clientId")), project.getClientId());
        assertEquals(ProjectType.valueOf(map.get("type")), project.getType());
        assertEquals(ProjectBillingType.valueOf(map.get("billingType")), project.getBillingType());
        assertEquals(map.get("startDate"), project.getStartDate().toString());
        if (map.get("endDate") != null) {
            assertEquals(map.get("endDate"), project.getEndDate().toString());
        }
        if (map.get("leaderId") != null) {
            assertEquals(map.get("leaderId"), project.getLeaderId());
        }
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @SuppressWarnings("unchecked")
    @Then("el proyecto debería tener un ID único")
    public void elProyectoDeberiaTenerUnIdUnico() {
        var getResponse = restTemplate.getForEntity(baseUrl + apiUrl, List.class);
        var projects = getResponse.getBody();

        Integer idFrequency = 0;
        for (var projectMap : projects) {
            Map<String, Object> map = (Map<String, Object>) projectMap;
            if (Long.valueOf(map.get("id").toString()).equals(project.getId())) {
                idFrequency++;
            }
        }

        assertNotNull(project.getId(), "Project ID should not be null"); 
        assertEquals(1, idFrequency, "Project ID should exist in the list of projects and be unique");
        
    }

    @Then("el proyecto debería tener un estado inicial {string}")
    public void elProyectoDeberiaTenerUnEstadoInicial(String expectedStatus) {
        assertNotNull(project, "Project should not be null");
        assertEquals(ProjectStatus.valueOf(expectedStatus), project.getStatus(), 
            "Project status should be " + expectedStatus);
    }

    @Then("se rechaza la creación del proyecto")
    public void seRechazaLaCreacionDelProyecto() {
        assertNull(project, "Project should be null for invalid data");
        assertNull(response, "Response should be null");

    }

    @Then("el proyecto debería tener 0 horas estimadas")
    public void elProyectoDeberiaTenerHorasEstimadas() {
        assertNotNull(project, "Project should not be null");
        assertEquals(0, project.getEstimatedHours(), 
            "Project estimated hours should be 0");
    }

}