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

        Group group = Group.builder().name("admins").build();

        Language en = Language.builder().code("en").build();
        Language ru = Language.builder().code("ru").build();

        User user1 = User.builder().username("brownfrog676").password("mandy").group(group).build();
        user1.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user1).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user1).build()
        ));

        User user2 = User.builder().username("redgorilla701").password("massimo").group(group).build();
        user2.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user2).build(),
                Post.builder().text("лол кек ват").language(ru).user(user2).build()
        ));

        User user3 = User.builder().username("crazyostrich818").password("newlife").group(group).build();
        user3.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user3).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user3).build()
        ));

        User user4 = User.builder().username("bigduck151").password("golfer1").group(group).build();
        user4.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user4).build(),
                Post.builder().text("лол кек ват").language(ru).user(user4).build()
        ));

        User user5 = User.builder().username("yellowgorilla497").password("talisman").group(group).build();
        user5.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user5).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user5).build()
        ));

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));

        log.info("--- /save ---");
    }

    @Transactional
    public void load() {
        log.info("---  load ---");
        long start = System.currentTimeMillis();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> page = userRepository.findAllByGroupAndLanguageAndQuery(1L, "ru", "а", pageRequest);
        List<User> users = page.getContent();
//        Page<User> page = postRepository.findAllByUserGroupIdAndLanguageCodeAndTextContaining(1L, "ru", "а", pageRequest).map(Post::getUser);
//        List<User> users = page.getContent();
        log.info("elapsed: {}", System.currentTimeMillis() - start);
        for (User user : users) {
            log.info("user: {}", user.toString());
        }
        log.info("--- /load ---");
    }
}
