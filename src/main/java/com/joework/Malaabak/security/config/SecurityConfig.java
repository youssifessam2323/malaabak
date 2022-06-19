package com.joework.Malaabak.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.joework.Malaabak.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final CustomUserDetailsService customUserDetailsService;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("starting security configurations...");
			http = http
					.cors()
						.and()
					.csrf().disable();
			
			http = http.sessionManagement().sessionCreationPolicy(STATELESS).and();
			
			http = http.exceptionHandling()
					.authenticationEntryPoint((req,res,ex) -> {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
					}).and();
			
		
				http = http.authorizeRequests().anyRequest().permitAll().and();
			
			http = http.headers().frameOptions().disable().and();
			// http = http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
			log.info("starting security configurations...");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("starting security configure(ManagerBuilder) method...");
		auth.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	   // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
    	log.info("settign CORS security configurations...");
        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    
    public AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManagerBean();
    }
    
    
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}
