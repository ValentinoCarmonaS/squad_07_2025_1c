package com.psa.proyecto_api.service;

import java.util.List;
import java.util.Map;

public interface ExternalApiService {
    List<Map<String, Object>> getRecursos(String skill, String availability, String role);
    Map<String, Object> getRecursoById(Integer id);
    List<Map<String, Object>> getClientes(String status, String type);
    Map<String, Object> getClienteById(Integer id);
    List<Map<String, Object>> getTicketsByTaskId(Long taskId);
}
