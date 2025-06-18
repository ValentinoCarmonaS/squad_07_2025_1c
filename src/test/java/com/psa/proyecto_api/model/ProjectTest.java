package com.psa.proyecto_api.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.psa.proyecto_api.ProyectoApiApplication;

@SpringBootTest(classes = ProyectoApiApplication.class)
@ActiveProfiles("test")
public class ProjectTest {
    @Test
    public void testProjectCreation() {
        // Test implementation
    }
} 