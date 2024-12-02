package com.lecsures.section2.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.lecsures.section2.exceptions.CustomBasicAuthenticationEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@Profile("!prod")
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests((req) -> req.anyRequest().authenticated());
        // http.authorizeHttpRequests((req) -> req.anyRequest().permitAll());

        // accept only http
        http.requiresChannel(requestChannelConfiguration -> requestChannelConfiguration.anyRequest().requiresInsecure());

        http.csrf(config -> config.disable());

        http.authorizeHttpRequests((req) -> req.requestMatchers("/api/accounts", "/api/balances", "/api/loans", "/api/cards").authenticated());
        http.authorizeHttpRequests((req) -> req.requestMatchers("/api/notices", "/api/contacts", "/api/welcomes","/api/users", "/error").permitAll());
        
        http.formLogin(withDefaults());
        // http.formLogin(formLoginConfig -> formLoginConfig);
        // http.formLogin((httpSecurityFormLoginConfig) -> httpSecurityFormLoginConfig.disable()); // chamge withDefaults() to disable() for disable login form
        
        // custom entry point for http basic
        http.httpBasic(httpBasicConfig -> httpBasicConfig.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        // http.httpBasic((httpBasicConfig) -> httpBasicConfig.disable());
        
        // global configuratinos for all exception from spring security
        http.exceptionHandling(exceptionHandlingConfig -> exceptionHandlingConfig.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        
        // global config for all exception for access denied handling
        // http.exceptionHandling(exceptionHandlingConfig -> exceptionHandlingConfig.accessDeniedHandler(new CustomAccessDeniedHandler()));
        
        // global config for all exception for access denied handling with custom page
        // http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied"));
        
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withUsername("user").password("{noop}12345").authorities("user").build();
    //     UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$aaHn3as7LcXWGJ3rSFqpxO//VzIefEkyW2sjNDqgh/5Uz9YZ.p6DG").authorities("admin").build();
        
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource) {
    //     return new JdbcUserDetailsManager(dataSource);
    // }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        // return password encoder implementation class
        // default of createDelegatingPasswordEncoder is BCrypt, modify by spring security team
        // BCrypt is one way encrypt password
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
