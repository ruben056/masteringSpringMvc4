package be.rd.msmvc.twitter.ctrl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import be.rd.msmvc.MasteringSpringMvc4Application;

@ActiveProfiles("redis")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MasteringSpringMvc4Application.class, StubTwitterSearchConfig.class})
public class SearchControllerStubTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void shouldSearch() throws Exception{
		mockMvc.perform(get("/mvc/twitter/search/mixed;keyWords=snow,avalanche"))
		.andExpect(status().isOk())
		.andExpect(view().name("/twitter/tweetsPage"))
		.andExpect(model().attribute("tweets", hasSize(2)))
		.andExpect(model().attribute("tweets", 
				hasItems(
				hasProperty("text", is(("stubbed1"))),
				hasProperty("text", is(("stubbed2")))
				)));
					
	}
	
}
