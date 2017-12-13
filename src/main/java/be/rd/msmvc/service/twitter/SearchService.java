package be.rd.msmvc.service.twitter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	private Twitter twitter;

	@Autowired
	public SearchService(Twitter twitter) {
		this.twitter = twitter;
	}

	public List<Tweet> search(String searchType, List<String> keywords) {
		
		
		List<SearchParameters> searches = keywords.stream()
				.map(keyword -> createSearchParam(searchType, keyword))
				.collect(Collectors.toList());
		
		List<Tweet> tweets = searches.stream()
				.map(params -> twitter.searchOperations().search(params))
				.flatMap(searchResults -> searchResults.getTweets().stream())
				.collect(Collectors.toList());
		
		return tweets;
	}

	private SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}

	private SearchParameters.ResultType getResultType(String searchType) {
		return Arrays.stream(SearchParameters.ResultType.values())
					.filter(knownType -> knownType.name()
					.equalsIgnoreCase(searchType)).findFirst()
					.orElse(SearchParameters.ResultType.RECENT);
	}
}
