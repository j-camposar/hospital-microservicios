package com.hospital.paciente.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hospital.paciente.DTO.LoginJWTDTO;
import com.hospital.paciente.DTO.ResponseDTO;

@Service
public class AuthService {
    private String secretKey = "mi_clave_super_segura";

    public ResponseDTO validar(LoginJWTDTO requesJwtdto){
        ResponseDTO responseDTO= new ResponseDTO();
           // 1. Simulación de validación de credenciales
        if ("admin".equals(requesJwtdto.getUsername()) && "1234".equals(requesJwtdto.getPassword())) {
            
            try {
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                
                // 2. Definir el tiempo de expiración (ej. 15 minutos)
                long expTime = System.currentTimeMillis() + (15 * 60 * 1000); 
                
                // 3. Crear el JWT
                String token = JWT.create()
                        .withSubject(requesJwtdto.getUsername()) // El dueño del token
                        .withExpiresAt(new java.util.Date(expTime))
                        .withClaim("roles", List.of("ROLE_ADMIN", "ROLE_PACIENTE")) // Los roles que tu Filtro va a leer
                        .sign(algorithm);
                responseDTO.setRespuestaInteger(0);
                responseDTO.setRespuestaText(token);
                // 4. Responder al cliente con el token en un Map o DTO
                return responseDTO;
                
            } catch (Exception e) {
                responseDTO.setRespuestaInteger(1);
                responseDTO.setRespuestaText("error en generar el token");
                
                return responseDTO;
            }
        }else{
            responseDTO.setRespuestaInteger(2);
            responseDTO.setRespuestaText("error, credenciales invalidas");
             
            return responseDTO;
        }
    }
}
