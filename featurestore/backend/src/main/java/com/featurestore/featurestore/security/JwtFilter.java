package com.featurestore.featurestore.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // rotas publicas nao precisan
        if (path.equals("/api/login") || path.equals("/api/registro")) {
            filterChain.doFilter(request, response);
            return;
        }

        // procura o token no header com o bearer
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token não fornecido.\"}");
            return;
        }

        String token = header.substring(7); //tirar o bearer

        if (!JwtUtil.isValidToken(token)) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido ou expirado.\"}");
            return;
        }

        // token aceito guarda o email na request para que seja usado
        String email = JwtUtil.getEmailFromToken(token);
        request.setAttribute("emailUsuario", email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null,Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        filterChain.doFilter(request, response);
    }
}