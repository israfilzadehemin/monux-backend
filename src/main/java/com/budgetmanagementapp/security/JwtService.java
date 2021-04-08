package com.budgetmanagementapp.security;

import com.budgetmanagementapp.utility.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:jwt.properties")
@Service
@Log4j2
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
        return Optional.ofNullable(request.getHeader(Constant.HEADER))
                .filter(header -> header.startsWith(Constant.PREFIX))
                .map(header -> header.substring(Constant.PREFIX.length()));
    }

    public Optional<Jws<Claims>> parseTokenToClaims(String token) {
        try {
            return Optional.of(
                    Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            );
        } catch (SignatureException ex) {
            log.error("JWT: Invalid signature");
        } catch (MalformedJwtException ex) {
            log.error("JWT: Invalid token");
        } catch (ExpiredJwtException ex) {
            log.error("JWT: Expired token");
        } catch (UnsupportedJwtException ex) {
            log.error("JWT: Unsupported token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT: token is empty.");
        }
        return Optional.empty();
    }

    public String getSubjectFromClaims(Jws<Claims> claimsJws) {
        return claimsJws.getBody().getSubject();
    }
}
