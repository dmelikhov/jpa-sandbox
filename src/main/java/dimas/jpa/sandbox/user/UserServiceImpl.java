package dimas.jpa.sandbox.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void run(ApplicationArguments args) {
        userService.save();
        userService.load();
    }

    @Transactional
    public void save() {
        log.info("---  save ---");

        Language en = Language.builder().code("en").build();
        Language ru = Language.builder().code("ru").build();

        User user1 = User.builder().username("hello").password("world").build();
        user1.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user1).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user1).build()
//                , Post.builder().text("аха что бар").language(ru).user(user1).build()
        ));

        User user2 = User.builder().username("super").password("admin").build();
        user2.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user2).build(),
                Post.builder().text("лол кек ват").language(ru).user(user2).build()
        ));

        userRepository.saveAll(List.of(user1, user2));

        log.info("--- /save ---");
    }

    @Transactional
    public void load() {
        log.info("---  load ---");
        long start = System.currentTimeMillis();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> page = userRepository.findAllByLanguageAndQuery("ru", "а", pageRequest);
        List<User> users = page.getContent();
        log.info("elapsed: {}", System.currentTimeMillis() - start);
        for (User user : users) {
            log.info("user: {}", user.toString());
        }
        log.info("--- /load ---");
    }
}
