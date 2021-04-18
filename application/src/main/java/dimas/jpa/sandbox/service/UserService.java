package dimas.jpa.sandbox.service;

import dimas.jpa.sandbox.domain.post.Post;
import dimas.jpa.sandbox.domain.post.PostRepository;
import dimas.jpa.sandbox.domain.user.User;
import dimas.jpa.sandbox.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private static final String ADMIN = "ADMIN";

    @PostConstruct
    public void init() {
        log.info("UserService.init");
        userService.save();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> userService.task(2)),
                CompletableFuture.runAsync(() -> userService.task(3))
        ).join();
        userService.load();
    }

    @Transactional
    public void save() {
        log.info("UserService.save");
        User user = new User().setUsername(ADMIN);
        setPosts(user, 1);
        userRepository.save(user);
    }

    @Transactional
    public void task(int x) {
        log.info("UserService.task[x={}]", x);
        User user = userRepository.findByUsername(ADMIN);
//        user.setUsername(user.getName() + x);
//        setPosts(user, x);
        userRepository.save(user);
    }

    private void setPosts(User user, int x) {
        user.getPosts().clear();
        user.addPost(new Post().setText("aaa" + x));
        user.addPost(new Post().setText("bbb" + x));
        user.addPost(new Post().setText("ccc" + x));
    }

    @Transactional
    public void load() {
        log.info("UserService.load");
        log.info("userRepository.findAll(): {}", userRepository.findAll());
    }
}
