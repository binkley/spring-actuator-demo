package hello;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import hello.RemoteHello.HystrixHello;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Api(value = "hello-world", description = "Hello, sunshine!")
@RestController
public class HelloWorldController {
    private final HystrixHello remote;

    @Inject
    public HelloWorldController(final HystrixHello remote) {
        this.remote = remote;
    }

    @ApiOperation(value = "sayHello", notes = "Howdy, world!")
    @ApiResponses({@ApiResponse(code = 200, message = "Okily, dokily!"),
            @ApiResponse(code = 203,
                    message = "Remote service unavailable")})
    @RequestMapping(value = "/hello-world/{name}", method = GET)
    @Timed
    public Greeting sayHello(
            @ApiParam(value = "name", required = true) @PathVariable
            final String name, final HttpServletResponse response) {
        // Fake enrich the data
        final Greeting greeting = remote.greet(name, response);
        return new Greeting(greeting.getMessage());
    }
}
