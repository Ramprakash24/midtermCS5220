package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@Test
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:springrest-servlet.xml","classpath:applicationContext.xml"})
public class GetUserTest extends AbstractTransactionalTestNGSpringContextTests{
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@BeforeClass
    void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( wac ).build();
    }
	
	@Test
	void getUserTest200() throws Exception{
		  this.mockMvc.perform( get( "/users/2" ) )
          .andExpect( status().isOk() )
          .andExpect( jsonPath( "username" ).value( "techit" ) );	
	}
	
	@Test
	void getUserTest403() throws Exception{
		  this.mockMvc.perform( get( "/users/2" ) )
          .andExpect( status().isOk() )
          .andExpect( jsonPath( "username" ).value( "jjim" ) );	
	}
	
	@Test
	void getUserTest404() throws Exception{
		  this.mockMvc.perform( get( "/users/6" ) )
          .andExpect( status().isOk() )
          .andExpect( jsonPath( "username" ).value( "techit" ) );	
	}
}
