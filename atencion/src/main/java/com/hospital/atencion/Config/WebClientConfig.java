package com.hospital.atencion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    // microservicio de tipoUsuario
    // @Bean
    // public WebClient webClientUsuario(WebClient.Builder builder){
    //     return builder.baseUrl("localhost:8080/api/v1").build();
    // }
    @Bean
    public WebClient webClientPaciente(WebClient.Builder builder){
        return builder.baseUrl("localhost:8081/api/v1").build();
    }
    // microservicio de medico
    @Bean
    public WebClient webClientMedico(WebClient.Builder builder){
        return builder.baseUrl("localhost:8083/api/v1").build();
    }
}
