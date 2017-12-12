package be.rd.msmvc.ctrl.twitter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mvc/twitter")
public class TweetCtrl {

	@Autowired
	private Twitter twitter;

	@RequestMapping("/")
	public String home() {
		return "twitter/searchPage";
	}

	@RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		
		if (search.toLowerCase().contains("struts")) {
			redirectAttributes.addFlashAttribute("error", "Try using spring instead!");
			return "redirect:/mvc/twitter";
		}	
		else {
			redirectAttributes.addAttribute("search", search);
			return "redirect:/mvc/twitter/result";
		}
	}

	@RequestMapping("/result")
	public String searchSomething(@RequestParam(defaultValue = "snow") String search, Model model) {

		SearchResults searchResults = twitter.searchOperations().search(search);
		List<Tweet> results = getFirstResults(searchResults, 10);

		model.addAttribute("tweets", results);
		model.addAttribute("search", search);
		return "twitter/tweetsPage";
	}

	private List<Tweet> getFirstResults(SearchResults searchResults, int nrOfResults) {

		if (searchResults.getTweets().isEmpty()) {
			return Collections.<Tweet>emptyList();
		}

		return searchResults.getTweets().stream().limit(nrOfResults).collect(Collectors.toList());
	}
}
