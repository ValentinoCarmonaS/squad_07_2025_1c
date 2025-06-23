package com.psa.proyecto_api.steps;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.psa.proyecto_api.model.enums.ProjectStatus;
import com.psa.proyecto_api.model.enums.ProjectType;
import com.psa.proyecto_api.dto.response.ProjectSummaryResponse;
import com.psa.proyecto_api.model.enums.ProjectBillingType;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VerListaProyectosSteps extends BaseCucumber {
    
    private List<ProjectSummaryResponse> projects;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Given("no existen proyectos en el sistema")
    public void noExistenProyectosEnElSistema() {
        projectBuilder.clearProjects();
        assertTrue(projectBuilder.getProjects().isEmpty());
    }

    @Given("existen los siguientes proyectos")
    public void existenLosSiguientesProyectos(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            projectBuilder.createProject(
                row.get("name"),
                Integer.parseInt(row.get("clientId")),
                ProjectType.valueOf(row.get("type")),
                ProjectBillingType.valueOf(row.get("billingType")),
                LocalDate.parse(row.get("startDate")),
                LocalDate.parse(row.get("endDate")),
                ProjectStatus.valueOf(row.get("status")),
                row.get("leaderId")
            );
        }        
    }

    @When("se solicita la lista de proyectos")
    public void elUsuarioSolicitaLaListaDeProyectos() {
        projects = projectBuilder.getProjects();
    }

    @Then("se pueden ver {int} proyectos en la lista")
    public void sePuedenVerProyectosEnLaLista(int expectedCount) {
        assertEquals(expectedCount, projects.size());
    }
    
    @And("están los proyectos con los siguientes nombres")
    public void estanLosProyectosConLosSiguientesNombres(List<String> names) {
        for (String name : names) {
            assertTrue(projects.stream().anyMatch(project -> project.getName().equals(name)));
        }
    }

    @And("cada proyecto debería mostrar las siguientes columnas")
    public void cadaProyectoDeberiaMostrarLasSiguientesColumnas(List<String> columns) {
        for (String column : columns) {
            for (ProjectSummaryResponse project : projects) {
                switch (column) {
                    case "id":
                        assertNotNull(project.getId());
                        break;
                    case "name":
                        assertNotNull(project.getName());
                        break;
                    case "clientId":
                        assertNotNull(project.getClientId());
                        break;
                    case "status":
                        assertNotNull(project.getStatus());
                        break;
                    case "startDate":
                        assertNotNull(project.getStartDate());
                        break;
                    case "endDate":
                        assertNotNull(project.getEndDate());
                        break;
                    case "tagNames":
                        assertTrue(project.getTagNames().isEmpty());
                        break;
                    default:
                        throw new IllegalArgumentException("Columna no válida: " + column);
                }
            }
        }
    }
}
