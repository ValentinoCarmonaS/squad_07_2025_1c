package com.psa.proyecto_api.service.impl;

import com.psa.proyecto_api.config.ExternalApiConfig;
import com.psa.proyecto_api.exception.ExternalServiceException;
import com.psa.proyecto_api.service.ExternalApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExternalApiServiceImpl implements ExternalApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalApiConfig externalApiConfig;

    @Override
    public List<Map<String, Object>> getRecursos(String skill, String availability, String role) {
        try {
            log.info("Consultando recursos externos con filtros: skill={}, availability={}, role={}", 
                     skill, availability, role);

            // Construir URL con parámetros de consulta
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(externalApiConfig.getRecursosApiUrl());
            
            if (skill != null) builder.queryParam("skill", skill);
            if (availability != null) builder.queryParam("availability", availability);
            if (role != null) builder.queryParam("role", role);

            String url = builder.toUriString();

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error al consultar API de recursos: {}", e.getMessage());
            throw new ExternalServiceException("No se pudo consultar la API de recursos", e);
        }
    }

    @Override
    public Map<String, Object> getRecursoById(Integer id) {
        try {
            log.info("Consultando recurso con ID: {}", id);
            
            String url = externalApiConfig.getRecursosApiUrl() + "/" + id;
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error al consultar recurso con ID {}: {}", id, e.getMessage());
            throw new ExternalServiceException("No se pudo consultar el recurso con ID " + id, e);
        }
    }

    @Override
    public List<Map<String, Object>> getClientes(String status, String type) {
        try {
            log.info("Consultando clientes externos con filtros: status={}, type={}", status, type);

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(externalApiConfig.getClientesApiUrl());
            
            if (status != null) builder.queryParam("status", status);
            if (type != null) builder.queryParam("type", type);

            String url = builder.toUriString();

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error al consultar API de clientes: {}", e.getMessage());
            throw new ExternalServiceException("No se pudo consultar la API de clientes", e);
        }
    }

    @Override
    public Map<String, Object> getClienteById(Integer id) {
        try {
            log.info("Consultando cliente con ID: {}", id);
            
            String url = externalApiConfig.getClientesApiUrl() + "/" + id;
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error al consultar cliente con ID {}: {}", id, e.getMessage());
            throw new ExternalServiceException("No se pudo consultar el cliente con ID " + id, e);
        }
    }

    @Override
    public List<Map<String, Object>> getTicketsByTaskId(Long taskId) {
        try {
            log.info("Consultando tickets para la tarea con ID: {}", taskId);

            String url = externalApiConfig.getSoporteApiUrl() + "/tickets?taskId=" + taskId;

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error al consultar tickets para tarea {}: {}", taskId, e.getMessage());
            throw new ExternalServiceException("No se pudo consultar los tickets para la tarea " + taskId, e);
        }
    }
}
