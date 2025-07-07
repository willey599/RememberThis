package com.willeylee.remember_this.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    System.out.println("@@@@@@@@@@@@@@@@@@@@ Building SecurityFilterChain... @@@@@@@@@@@@@@@@@@");

    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
            .anyRequest().denyAll()
        )
        .oauth2Login(oauth -> 
            oauth.successHandler((request, response, authentication) -> {
                response.sendRedirect("http://localhost:4200/");
            })
        );

    return http.build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return JwtDecoders.fromIssuerLocation("https://accounts.google.com");
    }
    
}