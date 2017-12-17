package be.rd.msmvc.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security config for anything but the rest endpoints
 * 
 * @author ruben
 *
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.formLogin().loginPage("/login")
			.defaultSuccessUrl("/mvc/twitter/profile")
			.and()
			.logout().logoutSuccessUrl("/login")
			.and()
			.authorizeRequests()
			.antMatchers("/webjars/**", "/login").permitAll()
			.anyRequest().authenticated();		
	}
}
