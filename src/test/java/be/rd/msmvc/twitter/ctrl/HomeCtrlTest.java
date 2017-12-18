package be.rd.msmvc.twitter.ctrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import be.rd.msmvc.SessionBuilder;

@ActiveProfiles("redis")
@RunWith(SpringRunner.class)
@SpringBootTest()
public class HomeCtrlTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void should_redirect_to_profile() throws Exception {
		this.mockMvc.perform(get("/mvc/twitter/"))
					.andDo(print())
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/mvc/twitter/profile"));
	}
	
	@Test
	public void should_redirect_to_tastes() throws Exception {
		
		MockHttpSession mockSession = new SessionBuilder().userTastes("snow","groovy").build();
		
		this.mockMvc.perform(get("/mvc/twitter/").session(mockSession))
					.andDo(print())
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/mvc/twitter/search/mixed;keyWords=snow,groovy"));
	}
}
