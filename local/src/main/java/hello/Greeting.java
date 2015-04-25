package hello;

import lombok.Data;

@Data
public class Greeting {
    private String message;

    public Greeting() {
    }

    public Greeting(final String message) {
        this.message = message;
    }
}
