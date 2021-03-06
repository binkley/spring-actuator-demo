package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableDiscoveryClient
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerMain {
    public static void main(final String... args) {
        SpringApplication.run(EurekaServerMain.class, args);
    }
}
