package be.rd.msmvc.twitter.ctrl;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import be.rd.msmvc.twitter.dto.LightTweet;
import be.rd.msmvc.twitter.service.ISearchService;

@ActiveProfiles("redis")
@RunWith(SpringRunner.class)
@SpringBootTest()
public class SearchCtrlMockTest {

	@Mock
	private ISearchService searchService;
	
	@InjectMocks
	private SearchCtrl searchCtrl;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(searchCtrl)
							.setRemoveSemicolonContent(false).build();
	}
	
	@Test
	public void shouldSearch() throws Exception{
		
		when(searchService.search("mixed", asList("snow", "avalanche")))
		.thenReturn(asList(
				new LightTweet("snowboarding rules"), 
				new LightTweet("but avalanches are dangerous")));
		
		mockMvc.perform(get("/mvc/twitter/search/mixed;keyWords=snow,avalanche"))
					.andExpect(status().isOk())
					.andExpect(view().name("/twitter/tweetsPage"))
					.andExpect(model().attribute("tweets", 
								everyItem(Matchers.hasProperty("text"))));
		
	}
}
