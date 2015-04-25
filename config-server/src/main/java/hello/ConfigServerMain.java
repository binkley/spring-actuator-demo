package hello;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableSwagger
@SpringBootApplication
public class ConfigServerMain {
    public static void main(final String... args) {
        SpringApplication.run(ConfigServerMain.class, args);
    }
}
