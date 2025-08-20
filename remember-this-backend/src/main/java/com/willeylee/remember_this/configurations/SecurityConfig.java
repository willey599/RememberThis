package com.willeylee.remember_this.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.willeylee.remember_this.services.CustomOidcUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOidcUserService customOidcUserService;

    public SecurityConfig(CustomOidcUserService customOidcUserService){
        this.customOidcUserService = customOidcUserService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@@@@@ Building SecurityFilterChain... @@@@@@@@@@@@@@@@@@");
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            //this creates the csrfTokenRepo and allows it to be handled when received
            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()) )
            
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
            )
            .oauth2Login(oauth -> 
                oauth.successHandler((request, response, authentication) -> {
                    response.sendRedirect("http://localhost:4200/dashboard");
                })
                .userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcUserService)))
            .logout(logout -> logout
            .logoutSuccessUrl("http://localhost:4200")
            );

        return http.build();
    }

    //preflight options request/cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    //specific handler for single page applications, taken from Spring Security Docs. Try to figure out exactly what this does later
    final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler{
        private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
        private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken){
            this.xor.handle(request, response, csrfToken);
            csrfToken.get();
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken){
            String headerValue = request.getHeader(csrfToken.getHeaderName());
            return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request, csrfToken);
        }
        


    }
}
    //Authorization code flow
    //When the user arrives at the login URL /api/login, the backend uses a redirect URI to redirect the user to the Google login endpoint.
    //upon logging in, Google sends an authorization code back through the browser. The browser immediately sends that code to the backend.
    //The backend makes a request to the Google token endpoint, exchanging that authorization code for an access token (which grants access to Google's resources pertaining to that user like photos, calendar, etc) AS WELL AS a JWT ID Token. 
    //This app doesn't use the access token after this, but the ID token contains the user's info. The backend sends the ID token through a series of cryptographic tests to ensure that its digital signature is authentic.
    //Once validated, the backend creates a user session object within the framework containing all of the session and user data. It sends a JSESSION cookie andn SRF token to the user through the browser. 
    //This JSESSION cookie is used by the user to make requests to the backend and is validated each time by the user session data inside the user object. 
    //In my app, the user OIDC info extracted from the JWT ID token is placed in the backend server to identify the user and retrieve their data. 
    //The backend protects its own resources this way, only allowing that user to access its data through the JSESSION cookie