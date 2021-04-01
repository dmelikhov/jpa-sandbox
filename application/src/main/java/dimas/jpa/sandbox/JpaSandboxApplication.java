package dimas.jpa.sandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class JpaSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaSandboxApplication.class, args);
    }
}
