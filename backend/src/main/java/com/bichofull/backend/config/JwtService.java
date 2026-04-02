package com.bichofull.backend.config;

import com.bichofull.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    // Chave secreta usada para assinar e validar a autenticidade do token
    private final String SECRET_KEY = "bichofull-secret-key-bichofull-secret-key";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Cria um novo token para o usuário que acabou de fazer login
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name()); // Guarda a permissão (ADMIN/USER) dentro do token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // Identifica o dono do token pelo e-mail
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Define validade de 1 hora
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assina o token com a chave secreta
                .compact();
    }

    // Lê o e-mail que está guardado dentro de um token já existente
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Lê o nível de acesso (Role) guardado no token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Verifica se o token é válido e se não foi alterado por terceiros
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Método privado que descriptografa e extrai todas as informações do token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}