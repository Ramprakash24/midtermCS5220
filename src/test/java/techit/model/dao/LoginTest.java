package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import techit.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Test
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:springrest-servlet.xml","classpath:applicationContext.xml"})
public class LoginTest extends AbstractTransactionalTestNGSpringContextTests{
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@BeforeClass
	void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup( wac ).build();
    }
	
	@Test
	void loginUserCorrectCredentials() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		User user = new User();
		
		this.mockMvc.perform( post("/login")
    			.contentType("application/json;charset=UTF-8")
    			.content(objectMapper.writeValueAsString(user)))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("techit"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.hash").value("$2a$10$v2/oF1tdBlXxejoMszKW3eNp/j6x8CxSBURUnVj006PYjYq3isJjO"));
	}
	
	@Test
	void loginUserWrongCredentials() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		User user = new User();
		
		this.mockMvc.perform( post("/login")
    			.contentType("application/json;charset=UTF-8")
    			.content(objectMapper.writeValueAsString(user)))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("techit"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.hash").value("$2a$10$v2/oF1tdBlXxejoMszKW3eNp/j6x8CxSBURUnVj006PYjYq3isJjD"));
	}
}
