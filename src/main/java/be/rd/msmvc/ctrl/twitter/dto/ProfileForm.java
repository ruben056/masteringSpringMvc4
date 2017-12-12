package be.rd.msmvc.ctrl.twitter.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileForm {
	
	private String twitterHandle;
	private String email;
	private LocalDate birthDate;
	private List<String> tastes = new ArrayList<>();

	
	public ProfileForm() {
		//required for thymeleaf to generate object...
	}
			
	public ProfileForm(String twitterHandle, String email, LocalDate birthDate, List<String> tastes) {
		this.twitterHandle = twitterHandle;
		this.email = email;
		this.birthDate = birthDate;
		this.tastes = tastes;
	}

	public String getTwitterHandle() {
		return twitterHandle;
	}


	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public List<String> getTastes() {
		return tastes;
	}


	public void setTastes(List<String> tastes) {
		this.tastes = tastes;
	}

	@Override
	public String toString() {
		return "ProfileForm [twitterHandle=" + twitterHandle + ", email=" + email + ", birthDate=" + birthDate
				+ ", tastes=" + tastes + "]";
	}
	

	
}
