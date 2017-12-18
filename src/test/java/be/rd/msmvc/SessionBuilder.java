package be.rd.msmvc;

import java.util.Arrays;

import org.springframework.mock.web.MockHttpSession;

import be.rd.msmvc.twitter.UserProfileSession;
import be.rd.msmvc.twitter.dto.ProfileForm;

public class SessionBuilder {
	private final MockHttpSession session;
	UserProfileSession sessionProfileBean;

	public SessionBuilder() {
		session = new MockHttpSession();
		sessionProfileBean = new UserProfileSession();
		session.setAttribute("scopedTarget.userProfileSession", sessionProfileBean);
	}

	public SessionBuilder userTastes(String... tastes) {
		sessionProfileBean.saveForm(new ProfileForm("", "", null, Arrays.asList("snow","groovy")));
		return this;
	}

	public MockHttpSession build() {
		return session;
	}
}
