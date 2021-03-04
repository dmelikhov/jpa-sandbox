package dimas.jpa.sandbox.user;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import dimas.jpa.sandbox.entity.Language;
import dimas.jpa.sandbox.entity.Post;
import dimas.jpa.sandbox.entity.Team;
import dimas.jpa.sandbox.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, ApplicationRunner {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void run(ApplicationArguments args) {
        userService.save();

        userService.load(); // warm up
        userService.send(userService.load());

        Stream.of(Logger.ROOT_LOGGER_NAME, "org.hibernate.SQL")
                .map(((LoggerContext) LoggerFactory.getILoggerFactory())::getLogger)
                .forEach(logger -> logger.setLevel(Level.WARN));
    }

    @Transactional
    public void save() {
        log.info("--- save started  ---");

        Team team = Team.builder().name("users").build();

        Language en = Language.builder().code("en").build();
        Language ru = Language.builder().code("ru").build();

        User user1 = User.builder().username("User #1").password("11111").team(team).build();
        user1.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user1).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user1).build()));

        User user2 = User.builder().username("User #2").password("22222").team(team).build();
        user2.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user2).build(),
                Post.builder().text("лол кек ват").language(ru).user(user2).build()));

        User user3 = User.builder().username("User #3").password("33333").team(team).build();
        user3.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user3).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user3).build()));

        User user4 = User.builder().username("User #4").password("44444").team(team).build();
        user4.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user4).build(),
                Post.builder().text("лол кек ват").language(ru).user(user4).build()));

        User user5 = User.builder().username("User #5").password("55555").team(team).build();
        user5.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user5).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user5).build()));

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));

        log.info("--- save finished ---");
    }

    @Transactional
    public Page<User> load() {
        log.info("--- load started  ---");
        long start = System.currentTimeMillis();

        Page<User> users = userRepository.findAllByTeamIdAndPostsLanguageCodeAndPostsTextContaining(
                1L, "ru", "а", PageRequest.of(0, 5));

        for (User user : users) {
            log.info("user: {}", user.toString());
        }

        log.info("--- load finished --- elapsed: {} ms", System.currentTimeMillis() - start);
        return users;
    }

    public void send(Page<User> users) {
        log.info("--- send started  ---");
        log.info("--- send finished ---");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
