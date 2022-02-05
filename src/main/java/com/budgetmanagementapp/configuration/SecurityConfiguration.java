package com.budgetmanagementapp.configuration;


import com.budgetmanagementapp.security.AuthenticationFilter;
import com.budgetmanagementapp.security.AuthorizationFilter;
import com.budgetmanagementapp.security.JwtService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.budgetmanagementapp.utility.UrlConstant.*;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthorizationFilter authorizationFilter;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(
                List.of("Authorization", "Cache-Control", "Content-Type", "X-PT-SESSION-ID", "NGSW-BYPASS", "token"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration
                .setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http
                .cors().configurationSource(request -> corsConfiguration);

        http
                .csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USER_LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_OTP_CONFIRM_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_CREATE_PASSWORD_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_FORGET_PASSWORD_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_RESET_PASSWORD_URL).permitAll()
                .antMatchers(HttpMethod.POST, USER_CREATE_INITIAL_ACCOUNT_URL).permitAll()
                .antMatchers(HttpMethod.GET, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL).permitAll()
                .antMatchers(HttpMethod.GET, ACCOUNT_GET_ALL_CURRENCIES_URL).permitAll()
                .antMatchers(HttpMethod.GET, BLOG_GET_ALL_BLOGS_URL).permitAll()
                .antMatchers(HttpMethod.GET, BLOG_GET_BLOG_BY_ID_URL).permitAll()
                .antMatchers(HttpMethod.GET, PLAN_GET_ALL_PLANS_URL).permitAll()
                .antMatchers(HttpMethod.GET, FEATURE_URL).permitAll()
                .antMatchers(HttpMethod.GET, DEFINITION_GET_ALL_DEFINITIONS_URL).permitAll()
                .antMatchers(HttpMethod.GET, STEP_GET_ALL_STEPS_URL).permitAll()
                .antMatchers(HttpMethod.GET, SERVICE_GET_ALL_SERVICES_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNER_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNER_BY_KEYWORD_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_GET_ALL_FAQS_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_URL).permitAll()

                .antMatchers(HttpMethod.POST, BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FEATURE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, FEATURE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, FEATURE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, DEFINITION_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, DEFINITION_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, DEFINITION_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FAQ_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, FAQ_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, FAQ_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, STEP_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, STEP_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, STEP_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, SERVICE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, SERVICE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, SERVICE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BANNER_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, BANNER_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, BANNER_URL).hasRole(UserRole.ADMIN.getRoleName())

                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()

                .anyRequest().authenticated();

        http
                .addFilter(new AuthenticationFilter(authenticationManager(), userService, jwtService));

        http
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
