package be.rd.msmvc.twitter.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.rd.msmvc.twitter.dto.LightTweet;
import be.rd.msmvc.twitter.service.SearchService;

@RestController
@RequestMapping(path = "/rest/twitter/search")
public class RestSearchCtrl {
	
	@Autowired
	private SearchService searchService;


	@RequestMapping(path = "/{searchType}", method=RequestMethod.GET)
	public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keyWords) {
		return searchService.search(searchType, keyWords);
	}
}
