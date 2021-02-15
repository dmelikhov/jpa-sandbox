package dimas.jpa.sandbox.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, ApplicationRunner {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

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

        User user = User.builder().username("hello").password("world").build();
        Post post = Post.builder().text("message").build();

        user.setPosts(List.of(post));

        userRepository.save(user);

        log.info("--- /save ---");
    }

    @Transactional
    public void load() {
        log.info("---  load ---");
        for (Post post : postRepository.findAll()) {
            log.info("post: {}", post);
        }
        log.info("--- /load ---");
    }
}
