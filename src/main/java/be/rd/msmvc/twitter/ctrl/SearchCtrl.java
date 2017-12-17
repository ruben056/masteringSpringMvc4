package be.rd.msmvc.twitter.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.rd.msmvc.twitter.dto.LightTweet;
import be.rd.msmvc.twitter.service.SearchService;


@Controller
public class SearchCtrl {

	@Autowired
	private SearchService searchService;

	@RequestMapping(path = "/mvc/twitter/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keyWords) {
		
		ModelAndView mav = new ModelAndView("/twitter/tweetsPage");
		
		List<LightTweet> tweets = searchService.search(searchType, keyWords);
		
		mav.addObject("tweets", tweets);
		mav.addObject("search", String.join(",", keyWords));
		
		return mav;
	}
}
