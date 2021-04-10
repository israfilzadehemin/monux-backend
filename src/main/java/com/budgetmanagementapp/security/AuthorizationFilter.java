package com.budgetmanagementapp.security;

import static com.budgetmanagementapp.utility.Constant.CONTENT_TYPE_JSON;

import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MUserDetailsService mUserDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            authorizeUser(request, response, chain);
        } catch (InvalidModelException | NullPointerException
                | SignatureException | MalformedJwtException | ExpiredJwtException
                | UnsupportedJwtException | IllegalArgumentException exception) {
            handleJwtException(response, exception);
        }
    }

    private void authorizeUser(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        jwtService.extractToken(req)
                .flatMap(jwtService::parseTokenToClaims)
                .map(jwtService::getSubjectFromClaims)
                .map(Long::parseLong)
                .map(mUserDetailsService::loadById)
                .map(ud -> new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities()))
                .ifPresent(auth -> {
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });

        chain.doFilter(req, res);
    }

    private void handleJwtException(HttpServletResponse response, RuntimeException exception) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(CONTENT_TYPE_JSON);
        objectMapper.writeValue(
                response.getOutputStream(),
                ResponseModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .body(exception.getMessage())
                        .build());

        logger.error(exception.getMessage());
    }
}
