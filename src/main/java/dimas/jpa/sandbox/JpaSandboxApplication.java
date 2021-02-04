package dimas.jpa.sandbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JpaSandboxApplication implements ApplicationRunner {

    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .id(42L)
                .username("hello")
                .password("world")
                .build();

        log.info("initial user : {}", user);

        final String json1 = "{\"username\":\"admin\"}";
        UserUpdateRequest request1 = objectMapper.readValue(json1, UserUpdateRequest.class);
        log.info("request #1   : {}", json1);
        objectMapper.updateValue(user, request1);
        log.info("user         : {}", user);

        final String json2 = "{\"username\":\"super\",\"password\":null}";
        UserUpdateRequest request2 = objectMapper.readValue(json2, UserUpdateRequest.class);
        log.info("request #2   : {}", json2);
        objectMapper.updateValue(user, request2);
        log.info("user         : {}", user);
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaSandboxApplication.class, args);
    }
}
