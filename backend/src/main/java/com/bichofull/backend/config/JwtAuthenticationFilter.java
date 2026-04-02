package com.bichofull.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Busca o cabeçalho "Authorization" na requisição HTTP
        final String authHeader = request.getHeader("Authorization");

        // Se o cabeçalho não existir ou não começar com "Bearer ", ignora e segue o fluxo
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Limpa a string para obter apenas o código do token JWT
        String token = authHeader.substring(7).replace("\"", "");

        // Se o token for válido e o usuário ainda não estiver autenticado no sistema
        if (jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                
            String email = jwtService.extractEmail(token);
            String role = jwtService.extractRole(token);

            // Cria um objeto de autenticação com o e-mail e as permissões (Roles) vindas do token
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Registra o usuário como "logado" no contexto de segurança do Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Deixa a requisição seguir para o Controller correspondente
        filterChain.doFilter(request, response);
    }
}