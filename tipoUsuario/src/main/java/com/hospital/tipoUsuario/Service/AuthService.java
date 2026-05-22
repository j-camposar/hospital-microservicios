package com.hospital.tipoUsuario.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hospital.tipoUsuario.DTO.LoginJWTDTO;
import com.hospital.tipoUsuario.DTO.ResponseDTO;

@Service
public class AuthService {
    private String secretKey = "mi_clave_secreta";
    private static final Logger log= LoggerFactory.getLogger(AuthService.class);

    public ResponseDTO validar(LoginJWTDTO requesJwtDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        // 1. Simulación de validación de credenciales
        if ("admin".equals(requesJwtDTO.getUsername())  && "1234".equals(requesJwtDTO.getPassword())) {
            try {
                log.info("El usuario "+requesJwtDTO.getUsername()+" logeado ");
                
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                // 2. Tiempo de expiración: 15 minutos
                long expTime = System.currentTimeMillis()
                + (15 * 60 * 1000);
                // 3. Crear el JWT
                String token = JWT.create()
                    .withSubject(requesJwtDTO.getUsername())
                    .withExpiresAt(new java.util.Date(expTime))
                    .withClaim("roles",
                        List.of("ROLE_ADMIN", "ROLE_PACIENTE"))
                    .sign(algorithm);

                    responseDTO.setRepuestaInt(0);
                    responseDTO.setToken(token);
                    return responseDTO;
            } catch (Exception e) {
                responseDTO.setRepuestaInt(1);
                responseDTO.setToken("error al generar token");
                return responseDTO;
            }
        } else {
            responseDTO.setRepuestaInt(2);
            responseDTO.setToken("credenciales inválidas");
            return responseDTO;
        }
    }
}