package com.hospital.atencion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
   // 1. Definimos el Builder primero
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean// anotacion para 
    public WebClient webClientPacientes(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/api/v1").build(); // Puerto del Micro de Paciente
    }

    @Bean
    public WebClient webClientMedicos(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8083/api/v1").build(); // Puerto del Micro de Médicos
    }
    
}
