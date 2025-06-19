package com.psa.proyecto_api.service;

import java.util.List;
import java.util.Map;

public interface ExternalApiService {
    List<Map<String, Object>> getResources();
    List<Map<String, Object>> getClients();
    // List<Map<String, Object>> getTicketsByTaskId(Long taskId);
    Map<String, Object> getClientById(Integer clientId);
    Map<String, Object>getResourceById(String resourceId);
}
