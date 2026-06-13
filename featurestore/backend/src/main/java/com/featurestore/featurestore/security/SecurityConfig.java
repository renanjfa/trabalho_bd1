package com.featurestore.featurestore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // desativar CSRF (nao precisa com jwt)
            .csrf(csrf -> csrf.disable())

            // configura CORS para que React possa hacer requests
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:5173"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            }))

            // Nao usar sesiones(nao usamos cookies usamos jwt) 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // rotas públicas e protegidas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/registro").permitAll()
                .anyRequest().authenticated()
            )

            // adicionar filtro JWT antes do filtro default de Spring
            .addFilterBefore(new JwtFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}