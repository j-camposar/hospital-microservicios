package com.hospital.paciente.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends OncePerRequestFilter{

    private final String secretKey = "mi_clave_super_segura";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Extraer el header "Authorization" de la petición HTTP
        String bearerToken = request.getHeader("Authorization");
        
        // 2. Verificar que el header exista y empiece con "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            try {
                // 3. Validar la firma del token con la clave secreta compartida
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                
                // 4. Extraer el usuario (Subject) y sus roles/permisos
                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                
                // 5. Convertir los roles de String a GrantedAuthority (lo que entiende Spring)
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                
                // 6. Crear el objeto de autenticación de Spring Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                
                // 7. Inyectar el usuario en el contexto de seguridad de esta petición
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
            } catch (Exception e) {
                // Si el token expiró, la firma no coincide o está alterado,
                // nos aseguramos de limpiar el contexto para que Spring lo rechace (403 Forbidden)
                SecurityContextHolder.clearContext();
            }
        }
        
        // 8. Pase lo que pase, dejar que la petición continúe su camino 
        // Si no había token o era inválido, los filtros posteriores de Spring interceptarán el paso
        filterChain.doFilter(request, response);
    }

}
