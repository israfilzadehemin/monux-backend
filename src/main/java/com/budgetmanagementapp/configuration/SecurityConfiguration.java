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

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**"
    };

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
                .antMatchers(HttpMethod.GET, PLANS_URL).permitAll()
                .antMatchers(HttpMethod.GET, PLANS_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, FEATURES_URL).permitAll()
                .antMatchers(HttpMethod.GET, FEATURES_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, DEFINITIONS_URL).permitAll()
                .antMatchers(HttpMethod.GET, DEFINITIONS_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, STEPS_URL).permitAll()
                .antMatchers(HttpMethod.GET, STEPS_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, SERVICES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SERVICES_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNERS_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNERS_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, BLOGS_URL).permitAll()
                .antMatchers(HttpMethod.GET, BLOGS_URL + CHILD_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_URL + CHILD_URL).permitAll()

                .antMatchers(HttpMethod.POST, BLOGS_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, BLOGS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, BLOGS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, PLANS_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, PLANS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, PLANS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FEATURES_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, FEATURES_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, FEATURES_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, DEFINITIONS_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, DEFINITIONS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, DEFINITIONS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FAQ_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, FAQ_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, FAQ_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, STEPS_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, STEPS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, STEPS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, SERVICES_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, SERVICES_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, SERVICES_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BANNERS_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.PUT, BANNERS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, BANNERS_URL + CHILD_URL).hasRole(UserRole.ADMIN.getRoleName())

                .antMatchers(AUTH_WHITELIST).permitAll()

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
