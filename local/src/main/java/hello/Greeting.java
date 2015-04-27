package hello;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class Greeting {
    private final String message;

    public Greeting(final String message) {
        this.message = message;
    }

    @ApiModelProperty(value = "message", notes = "Howdy!", required = true)
    public String getMessage() {
        return message;
    }
}
