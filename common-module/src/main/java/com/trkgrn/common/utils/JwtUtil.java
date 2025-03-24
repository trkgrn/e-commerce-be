package com.trkgrn.common.utils;


import com.trkgrn.common.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access-token.expiry.seconds}")
    private Long ACCESS_TOKEN_EXPIRY;

    @Value("${jwt.refresh-token.expiry.seconds}")
    private Long REFRESH_TOKEN_EXPIRY;

    private static final String CLAIM_KEY_ROLE = "role";

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public String extractRole(String token) {
        return extractClaim(token, claims -> (String) claims.get(CLAIM_KEY_ROLE));
    }

    public long tokenExpiredSeconds(String token) {
        long endTime = extractExpiration(token).getTime();
        long currentTime = new Date().getTime();
        return TimeUnit.MILLISECONDS.toSeconds(endTime - currentTime);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getKey()).build();
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userId, String type, String role) {
        Map<String, Object> claims = new HashMap<>();
        if (ApplicationConstants.ACCESS_TOKEN.equals(type)) {
            return createToken(claims, userId, ACCESS_TOKEN_EXPIRY, role);
        } else if (ApplicationConstants.REFRESH_TOKEN.equals(type)) {
            return createToken(claims, userId, REFRESH_TOKEN_EXPIRY, role);
        }
        return null;
    }

    private String createToken(Map<String, Object> claims, String subject, Long expireTime, String role) {
        claims.put(CLAIM_KEY_ROLE, role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expireTime)))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean validateToken(String token, String id) {
        if (Objects.isNull(token) || Objects.isNull(id)) {
            return false;
        }
        final String subject = extractSubject(token);
        return (subject.equals(id) && !isTokenExpired(token));
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);
        if (Objects.nonNull(authHeader) && authHeader.startsWith(ApplicationConstants.TOKEN_PREFIX)) {
            return authHeader.substring(7);
        }
        return null;
    }

}
