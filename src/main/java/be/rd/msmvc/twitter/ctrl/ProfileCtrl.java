package be.rd.msmvc.twitter.ctrl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.rd.msmvc.config.formatter.USLocalDateTimeFormatter;
import be.rd.msmvc.twitter.UserProfileSession;
import be.rd.msmvc.twitter.dto.ProfileForm;

@Controller
@RequestMapping("/mvc/twitter")
public class ProfileCtrl {

	
	@Autowired
	private UserProfileSession userProfileSession;
	
	/**
	 * This exposes an attribute to the webpage/view similar to the
	 * model.addAttribute...()
	 */
	@ModelAttribute(name = "dateFormat")
	public String localeFormat(Locale locale) {
		return USLocalDateTimeFormatter.getPattern(locale);
	}

	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayProfile(ProfileForm profileForm) {
		return "twitter/profile/profilePage";
	}

	@RequestMapping(path = "/profile", params = { "save" }, method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "twitter/profile/profilePage";
		}
		userProfileSession.saveForm(profileForm);
		return "redirect:/mvc/twitter/search/mixed;keyWords=" 
						+ String.join(",", profileForm.getTastes());
	}

	@RequestMapping(path = "/profile", params = { "addRow" }, method = RequestMethod.POST)
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add("");
		return "twitter/profile/profilePage";
	}

	@RequestMapping(path = "/profile", params = { "removeTaste" }, method = RequestMethod.POST)
	public String removeTaste(ProfileForm profileForm, HttpServletRequest req) {

		Integer rowId = Integer.parseInt(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "twitter/profile/profilePage";
	}
}
