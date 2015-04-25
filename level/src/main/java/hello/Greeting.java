package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = false)
public class Greeting
        extends ResourceSupport {
    private String message;

    public Greeting() {
    }

    @JsonCreator
    public Greeting(@JsonProperty("content") final String message) {
        this.message = message;
    }
}
