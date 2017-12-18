package be.rd.msmvc.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import be.rd.msmvc.twitter.interfaces.AuthenticatingSignInAdapter;

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
		http.formLogin().loginPage("/login").loginProcessingUrl("/login")
			.defaultSuccessUrl("/mvc/twitter/profile")
			.and()
			.logout().logoutSuccessUrl("/login")
			.and()
			.authorizeRequests()
			.antMatchers("/webjars/**", 
						"/login", 
						"/mvc/twitter/signin/**", "/signin/**", 
						"/mvc/twitter/signup/**", "/signup/**")
						.permitAll()
			.anyRequest().authenticated();		
	}
	
	@Bean
	@Primary
    public ProviderSignInController providerSignInController(
                ConnectionFactoryLocator connectionFactoryLocator,
                UsersConnectionRepository usersConnectionRepository, AuthenticatingSignInAdapter signInAdapter) {
        ProviderSignInController controller = new ProviderSignInController(
            connectionFactoryLocator, usersConnectionRepository,
            signInAdapter);
        controller.setSignUpUrl("/mvc/twitter/signup");
        controller.setPostSignInUrl("/mvc/twitter/profile");
        return controller;
    }
}
