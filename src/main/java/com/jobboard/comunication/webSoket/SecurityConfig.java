package com.jobboard.comunication.webSoket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Obbligatorio per le API/WebSocket
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/ws-messaging/**").permitAll() // Sblocca l'endpoint e i suoi sottopercorsi
            	    .requestMatchers("/api/chat/**").permitAll()
            	    .anyRequest().authenticated()
            	)
            .httpBasic(basic -> basic.disable()) // Disabilita quel popup che vedi nell'immagine
            .formLogin(form -> form.disable()); // Disabilita il form di login di default di Spring
            
        return http.build();
    }
}