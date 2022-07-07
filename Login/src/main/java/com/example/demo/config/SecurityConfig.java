package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	// Hashing passwords
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	// User's login authentication
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("Haruhiko").password(passwordEncoder().encode("admin325")).roles("USER");
//	}
	
	// Setting up authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Setting cors
        http.cors().configurationSource(this.corsConfigurationSource());
		// Authentication
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/api/login").permitAll()
			.antMatchers("/api/**").authenticated(); // login required
		// Using this filter when login
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		// Setting Login Filter
		http.addFilterAfter(new LoginFilter(), JWTAuthenticationFilter.class);
		// Ignore CSRF to use cookies
		http.csrf().ignoringAntMatchers("/api/**");
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
        corsConfiguration.addAllowedOrigin("http://localhost:8082");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsSource;
    }

}
