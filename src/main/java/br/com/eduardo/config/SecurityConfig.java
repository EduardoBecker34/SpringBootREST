package br.com.eduardo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.eduardo.security.jwt.JwtConfigurer;
import br.com.eduardo.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable() //Desabilita validação HTTP Basic
			.csrf().disable() //Desabilita o CSRF
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Não vai guardar estado
			.and()
				.authorizeRequests() //Para autorizar requisições 
				.antMatchers("/auth/signin", "/api-docs/**", "swagger-ui.html**").permitAll() //Para documentação liberar tudo
				.antMatchers("/api/**").authenticated() //Para acessos API precisa autenticar
				.antMatchers("/users").denyAll() //Bloquear acessos
			.and()
				.apply(new JwtConfigurer(tokenProvider));
	}

}
