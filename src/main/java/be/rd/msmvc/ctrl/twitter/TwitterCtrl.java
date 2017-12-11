package be.rd.msmvc.ctrl.twitter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwitterCtrl {

	@Autowired
	private Twitter twitter;
	
	@RequestMapping("/mvc/twitter")
	public String searchSomething(@RequestParam(defaultValue="snow") String search, Model model) {
		
		SearchResults searchResults = twitter.searchOperations().search(search);
		List<String> results = getFirstResults(searchResults, 10);
		
		model.addAttribute("tweets", results);
		return "tweetsPage";
	}

	private List<String> getFirstResults(SearchResults searchResults, int nrOfResults) {
		
		if(searchResults.getTweets().isEmpty()) {
			return Collections.<String>emptyList();
		}
		
		return searchResults.getTweets().stream()
		 		.map(Tweet::getText)
		 		.limit(nrOfResults)
		 		.collect(Collectors.toList());
	}
}
