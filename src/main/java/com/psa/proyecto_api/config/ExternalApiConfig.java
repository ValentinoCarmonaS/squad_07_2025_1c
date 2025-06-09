package com.psa.proyecto_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalApiConfig {

    // URLs de las APIs externas (desde application.properties o .env)
    @Value("${RECURSOS_API_URL}")
    private String recursosApiUrl;

    @Value("${CLIENTES_API_URL}")
    private String clientesApiUrl;

    @Value("${SOPORTE_API_URL}")
    private String soporteApiUrl;

    // Getters para usar en los servicios
    public String getRecursosApiUrl() {
        return recursosApiUrl;
    }

    public String getClientesApiUrl() {
        return clientesApiUrl;
    }

    public String getSoporteApiUrl() {
        return soporteApiUrl;
    }
}
