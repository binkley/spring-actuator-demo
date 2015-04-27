package hello;

import hello.RemoteHello.HystrixHello;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * {@code HelloWorldControllerTest} tests {@link HelloWorldController}.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class HelloWorldControllerTest {
    private HystrixHello remote;
    private MockMvc mvc;

    @Before
    public void setUp()
            throws Exception {
        remote = mock(HystrixHello.class);
        mvc = standaloneSetup(new HelloWorldController(remote)).build();
    }

    @Test
    public void getHello()
            throws Exception {
        when(remote.greet(eq("Bob"), any(HttpServletResponse.class))).
                thenReturn(new Greeting("Hello, Bob!"));

        mvc.perform(get("/hello-world/Bob").
                accept(APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.message", is(equalTo("Hello, Bob!"))));
    }
}
