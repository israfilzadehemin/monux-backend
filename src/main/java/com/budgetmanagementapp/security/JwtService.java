package com.budgetmanagementapp.security;

import com.budgetmanagementapp.utility.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:jwt.properties")
@Service
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry.default}")
    private long expiryDefault;

    @Value("${jwt.expiry.remember}")
    private long expiryRememberMe;

    public String generateToken(long userId, boolean rememberMe) {
        final Date now = new Date();
        final long delta = rememberMe ? expiryRememberMe : expiryDefault;
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + delta))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(Constant.JWT_HEADER))
                .filter(header -> header.startsWith(Constant.JWT_PREFIX))
                .map(header -> header.substring(Constant.JWT_PREFIX.length()));
    }

    public Optional<Jws<Claims>> parseTokenToClaims(String token) {
        return Optional.of(
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token));
    }

    public String getSubjectFromClaims(Jws<Claims> claimsJws) {
        return claimsJws.getBody().getSubject();
    }
}
