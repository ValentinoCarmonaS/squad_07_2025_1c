package com.psa.proyecto_api.steps;

import static org.junit.Assert.*;

import com.psa.proyecto_api.BaseIntegrationTest;
import com.psa.proyecto_api.builders.ProjectTestDataBuilder;
import com.psa.proyecto_api.builders.TaskTestDataBuilder;
import com.psa.proyecto_api.dto.response.ProjectResponse;
import com.psa.proyecto_api.dto.response.TaskResponse;

import io.cucumber.java.Before;


public class BaseCucumber extends BaseIntegrationTest {
        
    protected ProjectTestDataBuilder projectBuilder;
    protected TaskTestDataBuilder taskBuilder;

    protected ProjectResponse project;
    protected TaskResponse task;


    @Override
    public void setUp() {
        super.setUp();
        taskBuilder = new TaskTestDataBuilder(getBaseUrl(), restTemplate);
        projectBuilder = new ProjectTestDataBuilder(getBaseUrl(), restTemplate, taskBuilder);
    }

}
