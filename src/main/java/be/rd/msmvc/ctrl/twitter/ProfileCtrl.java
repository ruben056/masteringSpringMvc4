package be.rd.msmvc.ctrl.twitter;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.rd.msmvc.config.formatter.USLocalDateTimeFormatter;
import be.rd.msmvc.ctrl.twitter.dto.ProfileForm;

@Controller
@RequestMapping("/mvc/twitter")
public class ProfileCtrl {

	/**
	 * This exposes an attribute to the webpage/view
	 * similar to the model.addAttribute...()
	 */
	@ModelAttribute(name="dateFormat")
	public String localeFormat(Locale locale) {
		return USLocalDateTimeFormatter.getPattern(locale);
	}
	
	@RequestMapping(path = "/profile" , method=RequestMethod.GET)
	public String displayProfile(ProfileForm profileForm) {
		return "twitter/profile/profilePage";
	}
	
	@RequestMapping(path = "/profile" , method=RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "twitter/profile/profilePage";	
		}
		return "redirect:/mvc/twitter/profile";
	}
}
