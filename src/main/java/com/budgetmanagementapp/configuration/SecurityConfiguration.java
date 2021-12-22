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

import static com.budgetmanagementapp.utility.UrlConstant.*;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthorizationFilter authorizationFilter;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
                .antMatchers(HttpMethod.GET, FEATURE_GET_ALL_FEATURES).permitAll()
                .antMatchers(HttpMethod.GET, DEFINITION_GET_ALL_DEFINITIONS_URL).permitAll()
                .antMatchers(HttpMethod.GET, STEP_GET_ALL_STEPS_URL).permitAll()
                .antMatchers(HttpMethod.GET, SERVICE_GET_ALL_SERVICES_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNER_GET_BANNER_BY_ID_URL).permitAll()
                .antMatchers(HttpMethod.GET, BANNER_GET_BANNER_BY_KEYWORD_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_GET_ALL_FAQS_URL).permitAll()
                .antMatchers(HttpMethod.GET, FAQ_GET_FAQ_BY_ID_URL).permitAll()

                .antMatchers(HttpMethod.POST, BLOG_CREATE_BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BLOG_UPDATE_BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BLOG_DELETE_BLOG_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, PLAN_ADD_PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, PLAN_UPDATE_PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, PLAN_DELETE_PLAN_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FEATURE_ADD_FEATURE).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FEATURE_UPDATE_FEATURE).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FEATURE_DELETE_FEATURE).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, DEFINITION_CREATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, DEFINITION_UPDATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, DEFINITION_DELETE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FAQ_CREATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FAQ_UPDATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, FAQ_DELETE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, STEP_CREATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, STEP_UPDATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, STEP_DELETE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, SERVICE_CREATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, SERVICE_UPDATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, SERVICE_DELETE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BANNER_CREATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BANNER_UPDATE_URL).hasRole(UserRole.ADMIN.getRoleName())
                .antMatchers(HttpMethod.POST, BANNER_DELETE_URL).hasRole(UserRole.ADMIN.getRoleName())

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
