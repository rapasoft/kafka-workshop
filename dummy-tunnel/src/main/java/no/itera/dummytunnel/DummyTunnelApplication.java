package no.itera.dummytunnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class DummyTunnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DummyTunnelApplication.class, args);
    }

}
