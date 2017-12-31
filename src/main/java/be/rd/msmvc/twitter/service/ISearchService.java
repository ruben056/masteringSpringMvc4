package be.rd.msmvc.twitter.service;

import java.util.List;

import be.rd.msmvc.twitter.dto.LightTweet;

public interface ISearchService {

	public List<LightTweet> search(String searchType, List<String> keywords);
}