package com.wm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
public class AppConfig {

	public static final String[] PUBLIC_API= {
			"/v3/api-docs",
			"/api/v1/auth/**",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"

	};


	@Bean
	public SecurityFilterChain securityConfigrationChain(HttpSecurity http) throws Exception {

		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/swagger-ui*/**",  "/v3/api-docs/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/api/clients/","/login","/signup").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilterAfter(new JwtGeneratorTokenFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.csrf().disable()
		.exceptionHandling().authenticationEntryPoint((req, res, authEx) -> res.sendRedirect("/welcome"))
		.and()
		.formLogin().loginProcessingUrl("/login")
		.and()
		.httpBasic();

		return http.build();

	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}


}
