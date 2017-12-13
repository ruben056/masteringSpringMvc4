package be.rd.msmvc.session.twitter;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import be.rd.msmvc.ctrl.twitter.dto.ProfileForm;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfileSession implements Serializable {
	private String twitterHandle;
	private String email;
	private LocalDate birthDate;
	private List<String> tastes = new ArrayList<>();
	
	private Path picturePath;

	public void saveForm(ProfileForm profileForm) {
		this.twitterHandle = profileForm.getTwitterHandle();
		this.email = profileForm.getEmail();
		this.birthDate = profileForm.getBirthDate();
		this.tastes = profileForm.getTastes();
	}

	public ProfileForm toForm() {
		ProfileForm profileForm = new ProfileForm();
		profileForm.setTwitterHandle(twitterHandle);
		profileForm.setEmail(email);
		profileForm.setBirthDate(birthDate);
		profileForm.setTastes(tastes);
		return profileForm;
	}
	
	
	public void setPicturePath(Path picturePath) {
		this.picturePath = picturePath;
	}
	
	public Path getPicturePath() {
		return picturePath;
	}
	
	public List<String> getTastes() {
		return tastes;
	}
	
}
