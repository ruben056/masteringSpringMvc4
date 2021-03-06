package be.rd.msmvc.twitter.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import be.rd.msmvc.twitter.dto.LightTweet;

@Service
public class SearchService implements ISearchService {
	private Twitter twitter;

	@Autowired
	public SearchService(Twitter twitter) {
		this.twitter = twitter;
	}

	@Override
	public List<LightTweet> search(String searchType, List<String> keywords) {
		
		
		List<SearchParameters> searches = keywords.stream()
				.map(keyword -> createSearchParam(searchType, keyword))
				.collect(Collectors.toList());
		
		List<LightTweet> tweets = searches.stream()
				.map(params -> twitter.searchOperations().search(params))
				.flatMap(searchResults -> searchResults.getTweets().stream())
				.map(LightTweet::offTweet)
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
