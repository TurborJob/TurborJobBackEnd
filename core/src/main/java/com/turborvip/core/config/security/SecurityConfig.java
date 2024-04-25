package com.turborvip.core.config.security;

import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.config.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final   JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(STATELESS)
                        .maximumSessions(100)
                        .maxSessionsPreventsLogin(false))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/**","OPTION")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/playground/**","GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/graphql/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/all/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/no-auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/login","POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/logout","POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).hasAnyAuthority(EnumRole.ROLE_USER.toString(),EnumRole.ROLE_SUPER_ADMIN.toString(),EnumRole.ROLE_ADMIN.toString(),EnumRole.MANAGER.toString())
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/user-only/**")).hasAnyAuthority(EnumRole.ROLE_USER.toString())
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/user/**")).hasAnyAuthority(EnumRole.ROLE_USER.toString(),EnumRole.ROLE_SUPER_ADMIN.toString(),EnumRole.ROLE_ADMIN.toString(),EnumRole.MANAGER.toString())
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasAnyAuthority(EnumRole.ROLE_SUPER_ADMIN.toString(),EnumRole.ROLE_ADMIN.toString())
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/manager/**")).hasAnyAuthority(EnumRole.ROLE_SUPER_ADMIN.toString(),EnumRole.ROLE_ADMIN.toString(),EnumRole.MANAGER.toString())
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/business/**")).hasAnyAuthority(EnumRole.MANAGER.toString())
                        .anyRequest().authenticated())

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Cho phép tất cả các domain
        configuration.setAllowedMethods(Arrays.asList("*")); // Cho phép tất cả các phương thức HTTP
        configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả các header
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
