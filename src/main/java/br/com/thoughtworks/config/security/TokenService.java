package br.com.thoughtworks.config.security;

import br.com.thoughtworks.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;
    @Value("${forum.jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("Forum API")
                .setSubject(((Usuario) auth.getPrincipal()).getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Long getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
