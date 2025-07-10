package uz.project.template.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.project.template.utils.ResMessages;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    @Value("${secret.jwt.key.access}")
    private String jwtKey;

    @Value("${secret.jwt.key.refresh}")
    private String refreshKey;

    @Value("${secret.jwt.expiration.access}")
    private Long expirationAccess;

    @Value("${secret.jwt.expiration.refresh}")
    private Long expirationRefresh;

    public Key getSignKey(String key){
        byte[] decode = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(decode);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String key, Long expiration) {
        Date now = new Date();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(Map<String, Object> extraClaims, String username, String key, Long expiration) {
        Date now = new Date();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshKey, expirationRefresh);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, jwtKey, expirationAccess);
    }

    public String generateAccessToken(String refreshToken) {
        Claims claims = parseToken(refreshToken, refreshKey);
        if (claims.getExpiration().after(new Date())) {
            String username = extractUsername(claims);
            return generateToken(new HashMap<>(), username, jwtKey, expirationAccess);
        }
        throw new RuntimeException(ResMessages.TOKEN_VALIDATION_FAILED);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, jwtKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    private boolean validateToken(String token, String key) {
        try {
            Claims claims = parseToken(token, key);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseToken(String token, String key) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token xatosi: " + e.getMessage());
        }
    }

    private String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    public String extractUsername(String token) {
        Claims claims = parseToken(token, jwtKey);
        return extractUsername(claims);
    }
}
