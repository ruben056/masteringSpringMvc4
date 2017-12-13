package be.rd.msmvc.ctrl.twitter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.rd.msmvc.session.twitter.UserProfileSession;

@Controller
@RequestMapping("/mvc/twitter")
public class HomeCtrl {

	@Autowired
	private UserProfileSession userProfileSession;
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home() {
		
		List<String> tastes = userProfileSession.getTastes();
		if(tastes.isEmpty()) {
			return "redirect:/mvc/twitter/profile";
		}
		else {
			return "redirect:/mvc/twitter/search/mixed;keyWords=" 
					+ String.join(",", tastes);
		}
	}
}
