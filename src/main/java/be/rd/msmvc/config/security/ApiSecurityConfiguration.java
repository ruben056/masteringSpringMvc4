package be.rd.msmvc.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class configures the security for the rest endpoints
 * @author ruben
 *
 */
@Configuration
@Order(1)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ruben").password("ruben").roles("USER").and().withUser("admin")
				.password("admin").roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.antMatcher("/api/**") // only /api/** urls!!
			.httpBasic()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
			.anyRequest().authenticated();
		
	}
}
