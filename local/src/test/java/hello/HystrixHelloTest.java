package hello;

import hello.RemoteHello.HystrixHello;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static javax.servlet.http.HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@code HystrixHelloTest} tests {@link HystrixHello}.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 * @todo Needs documentation
 */
@RunWith(MockitoJUnitRunner.class)
public class HystrixHelloTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public HttpServletResponse response;
    @Mock
    public RemoteHello remote;
    @Mock
    public Random random;

    private HystrixHello wrapper;

    @Before
    public void setUp() {
        wrapper = new HystrixHello(remote, random);
    }

    @Test
    public void shouldGreetNicely() {
        when(random.nextBoolean()).
                thenReturn(true);
        final Greeting greeting = new Greeting("Hello, Bob!");
        when(remote.greet(eq("Bob"))).
                thenReturn(greeting);

        assertThat(wrapper.greet("Bob", response).getMessage(),
                is(equalTo(greeting.getMessage())));
    }

    @Test
    public void shouldGreetBadly() {
        thrown.expect(IllegalStateException.class);
        when(random.nextBoolean()).
                thenReturn(false);

        wrapper.greet("Bob", response);
    }

    @Test
    public void shouldGoAwayRudely() {
        assertThat(wrapper.goAway("Bob", response).getMessage(),
                is(equalTo("You're not welcome, Bob.")));
    }

    @Test
    public void shouldGoAwaySoftly() {
        wrapper.goAway("Bob", response);

        verify(response).setStatus(eq(SC_NON_AUTHORITATIVE_INFORMATION));
    }
}
