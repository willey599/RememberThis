package com.willeylee.remember_this.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.OPTIONS, "/").permitAll();
            auth.anyRequest().authenticated();
            }   
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
        //this is ok for production but remember to be more specific with allowed headers once you know what kinds of headers to expect from your front end
        config.setAllowedHeaders(List.of("*"));
        //enables sending cookies through your browser. Must be enabled to do this
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        //This allows you to attach certain CorsConfiguration configs to certain Url patterns 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    
}