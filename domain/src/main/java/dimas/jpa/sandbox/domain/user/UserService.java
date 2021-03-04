package dimas.jpa.sandbox.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        hello("aa");
        hello(null);
        hello(Map.of("foo", "bar").get("baz"));
    }

    private void hello(@NonNull Object world) {
        log.info("world: {}", world.toString());
    }
}
