package com.iwa.utilisateurs.security;

import com.iwa.utilisateurs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private CustomUserDetailsService userDetailsService;

    //private JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Enable this config to allow all requests but let the class extends WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection
                .authorizeRequests()
                .anyRequest().permitAll(); // Allow all requests
    }

    // Enable this config to allow only authenticated requests with a valid JWT with filters but remove the class extends WebSecurityConfigurerAdapter
    /**
     *     @Bean
     *     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     *         http
     *                 .csrf().disable()
     *                 .exceptionHandling()
     *                 .authenticationEntryPoint(authEntryPoint)
     *                 .and()
     *                 .sessionManagement()
     *                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     *                 .and()
     *                 .authorizeRequests()
     *                 .antMatchers("/api/auth/**").permitAll()
     *                 .anyRequest().authenticated()
     *                 .and()
     *                 .httpBasic();
     *         http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
     *         return http.build();
     *     }
     * */


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Enable this config to allow only authenticated requests with a valid JWT with filters but remove the class extends WebSecurityConfigurerAdapter
    /**
     *     @Bean
     *     public  JWTAuthenticationFilter jwtAuthenticationFilter() {
     *         return new JWTAuthenticationFilter();
     *     }
     * */

}

