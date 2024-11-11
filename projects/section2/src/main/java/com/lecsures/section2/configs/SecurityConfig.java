package com.lecsures.section2.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests((req) -> req.anyRequest().authenticated());
        // http.authorizeHttpRequests((req) -> req.anyRequest().permitAll());

        http.authorizeHttpRequests((req) -> req.requestMatchers("/api/accounts", "/api/balances", "/api/loans", "/api/cards").authenticated());
        http.authorizeHttpRequests((req) -> req.requestMatchers("/api/notices", "/api/contacts", "/error").permitAll());

        http.formLogin(withDefaults());
        // http.formLogin((httpSecurityFormLoginConfig) -> httpSecurityFormLoginConfig.disable()); // chamge withDefaults() to disable() for disable login form
        
        http.httpBasic(withDefaults());
        // http.httpBasic((httpBasicConfig) -> httpBasicConfig.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("user").build();
        UserDetails admin = User.withUsername("admin").password("{noop}54321").authorities("admin").build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }

}
