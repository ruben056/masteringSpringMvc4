package be.rd.msmvc.twitter.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import be.rd.msmvc.twitter.interfaces.AuthenticatingSignInAdapter;;

@Controller
public class SignupCtrl {

	private final ProviderSignInUtils signInUtils;

	@Autowired
	public SignupCtrl(ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository connectionRepository) {
		signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
	}
	
	@RequestMapping("/mvc/twitter/signup")
	public String signUp(WebRequest request) {
		Connection<?> connection = signInUtils.getConnectionFromSession(request);
		if(connection != null) {
			AuthenticatingSignInAdapter.authenticate(connection);
			signInUtils.doPostSignUp(connection.getDisplayName(), request);
		}
		return "redirect:/mvc/twitter/profile";
	}

}
