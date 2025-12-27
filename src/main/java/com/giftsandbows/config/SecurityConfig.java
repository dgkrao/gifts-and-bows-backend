package com.giftsandbows.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF (API-based auth)
            .csrf(csrf -> csrf.disable())

            // ✅ ENABLE CORS (uses CorsConfig bean)
            .cors(Customizer.withDefaults())

            // Stateless session (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Authorization rules
            .authorizeHttpRequests(auth -> auth

                // ✅ ROOT & HEALTH (Render + browser)
                .requestMatchers(
                    "/",
                    "/health",
                    "/error"
                ).permitAll()

                // 🔓 PUBLIC APIs
                .requestMatchers(
                    "/api/auth/**",
                    "/api/categories/**",
                    "/api/products/**",
                    "/uploads/**"
                ).permitAll()

                // 🔐 ADMIN APIs
                .requestMatchers("/api/admin/**")
                .hasAuthority("ADMIN")

                // 🔒 EVERYTHING ELSE
                .anyRequest().authenticated()
            )

            // JWT filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            // Disable default login mechanisms
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
