package com.budgetmanagementapp.configuration;

import static com.budgetmanagementapp.utility.Constant.H2_CONSOLE_URL;
import static com.budgetmanagementapp.utility.Constant.USER_LOGIN_URL;

import com.budgetmanagementapp.security.AuthenticationFilter;
import com.budgetmanagementapp.security.AuthorizationFilter;
import com.budgetmanagementapp.security.JwtService;
import com.budgetmanagementapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .antMatchers(H2_CONSOLE_URL).permitAll()
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
