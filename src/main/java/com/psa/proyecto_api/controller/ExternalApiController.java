package com.psa.proyecto_api.controller;

import com.psa.proyecto_api.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ExternalApiController {

    @Autowired
    private ExternalApiService externalApiService;

    // GET /recursos - Consultar recursos disponibles (pool de desarrolladores)
    @GetMapping("/recursos")
    public ResponseEntity<List<Map<String, Object>>> getRecursos(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String availability,
            @RequestParam(required = false) String role) {
        
        List<Map<String, Object>> recursos = externalApiService.getRecursos(skill, availability, role);
        return ResponseEntity.ok(recursos);
    }

    // GET /clientes - Consultar clientes disponibles
    @GetMapping("/clientes")
    public ResponseEntity<List<Map<String, Object>>> getClientes(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type) {
        
        List<Map<String, Object>> clientes = externalApiService.getClientes(status, type);
        return ResponseEntity.ok(clientes);
    }

    // GET /clientes/{id} - Obtener información de un cliente específico
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Object>> getClienteById(@PathVariable Integer id) {
        Map<String, Object> cliente = externalApiService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    // GET /soporte/tickets - Consultar tickets asociados a una tarea
    @GetMapping("/soporte/tickets")
    public ResponseEntity<List<Map<String, Object>>> getTicketsByTaskId(
            @RequestParam Long taskId) {
        
        List<Map<String, Object>> tickets = externalApiService.getTicketsByTaskId(taskId);
        return ResponseEntity.ok(tickets);
    }

    // GET /recursos/{id} - Obtener información de un recurso específico
    @GetMapping("/recursos/{id}")
    public ResponseEntity<Map<String, Object>> getRecursoById(@PathVariable Integer id) {
        Map<String, Object> recurso = externalApiService.getRecursoById(id);
        return ResponseEntity.ok(recurso);
    }
}
