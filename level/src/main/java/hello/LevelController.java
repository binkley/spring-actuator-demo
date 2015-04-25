package hello;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.lang.String.format;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class LevelController {
    @RequestMapping(value = "/greeting", method = GET)
    @ResponseBody
    public HttpEntity<Greeting> greet(
            @RequestParam(value = "name", required = false,
                    defaultValue = "World") final String name) {
        final Greeting greeting = new Greeting(format("Hello, %s!", name));
        greeting.add(linkTo(methodOn(LevelController.class).greet(name)).
                withSelfRel());
        return new ResponseEntity<>(greeting, OK);
    }
}
