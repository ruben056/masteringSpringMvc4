package be.rd.msmvc.twitter.ctrl;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import be.rd.msmvc.twitter.dto.LightTweet;
import be.rd.msmvc.twitter.service.ISearchService;

@Configuration
public class StubTwitterSearchConfig {

	@Primary
	@Bean
	public ISearchService searchService() {
		return new ISearchService() {
			
			@Override
			public List<LightTweet> search(String searchType, List<String> keywords) {
				return Arrays.asList(new LightTweet("stubbed1"), new LightTweet("stubbed2"));
			}
		};
	}
}
