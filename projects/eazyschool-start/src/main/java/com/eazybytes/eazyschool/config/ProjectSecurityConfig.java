package com.eazybytes.eazyschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.eazybytes.eazyschool.handlers.CustomAuthenticationFailureHandler;
import com.eazybytes.eazyschool.handlers.CustomAuthenticationSuccessHandler;

@Configuration
public class ProjectSecurityConfig {

	private final CustomAuthenticationSuccessHandler successHandler;
	private final CustomAuthenticationFailureHandler failureHandler;

	@Autowired
	public ProjectSecurityConfig(CustomAuthenticationSuccessHandler successHandler,
			CustomAuthenticationFailureHandler failureHandler) {
		super();
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").permitAll()
						.requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg", "/courses", "/about",
								"/assets/**", "/login/**")
						.permitAll())
				.formLogin(flc -> flc.loginPage("/login") // invoke login page
						.usernameParameter("usern") // custom field username from HMTL
						.passwordParameter("pwd") // custom field password from HTML
						.defaultSuccessUrl("/dashboard") // set default success url after login successful
						.failureUrl("/login?error=true") // set default url after login failed
						.failureHandler(this.failureHandler) // handle success event
						.successHandler(this.successHandler) // handle failed event
				).httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password("{noop}EazyBytes@12345").authorities("read").build();
		UserDetails admin = User.withUsername("admin")
				.password("{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m").authorities("admin")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * From Spring Security 6.3 version
	 *
	 * @return
	 */
	@Bean
	CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}

}
