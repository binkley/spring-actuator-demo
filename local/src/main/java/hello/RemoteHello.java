package hello;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * {@code RemoteHello} is an interface for Feign and Hystrix wrapper for the
 * {@code remote-hello} service.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 * @todo Needs documentation
 */
@FeignClient("remote-hello")
public interface RemoteHello {
    @RequestMapping(value = "/remote-hello", method = GET)
    Greeting greet(@RequestParam("name") final String name);

    @Component
    class HystrixHello {
        private static final Random R = new Random();

        private final Random r;
        private final RemoteHello remote;

        @Inject
        public HystrixHello(final RemoteHello remote) {
            this(remote, R);
        }

        /** For testing. */
        HystrixHello(final RemoteHello remote, final Random random) {
            this.remote = remote;
            r = random;
        }

        @HystrixCommand(fallbackMethod = "goAway")
        public Greeting greet(final String name,
                final HttpServletResponse ignored) {
            // 7-in-8 chance of success
            if (r.nextBoolean() || r.nextBoolean() || r.nextBoolean())
                return remote.greet(name);
            else
                throw new IllegalStateException("Bleh!");
        }

        public Greeting goAway(final String name,
                final HttpServletResponse response) {
            response.setStatus(SC_NON_AUTHORITATIVE_INFORMATION);
            return new Greeting(format("You're not welcome, %s.", name));
        }
    }
}
