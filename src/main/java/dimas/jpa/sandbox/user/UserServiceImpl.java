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

        final String hibernateSqlLoggerName = "org.hibernate.SQL";
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        Stream.of(Logger.ROOT_LOGGER_NAME, hibernateSqlLoggerName)
                .map(context::getLogger)
                .forEach(logger -> logger.setLevel(Level.WARN));
    }

    @Transactional
    public void save() {
        log.info("--- save started  ---");

        Team team = Team.builder().name("admins").build();

        Language en = Language.builder().code("en").build();
        Language ru = Language.builder().code("ru").build();

        User user1 = User.builder().username("brownfrog676").password("mandy").team(team).build();
        user1.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user1).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user1).build()
        ));

        User user2 = User.builder().username("redgorilla701").password("massimo").team(team).build();
        user2.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user2).build(),
                Post.builder().text("лол кек ват").language(ru).user(user2).build()
        ));

        User user3 = User.builder().username("crazyostrich818").password("newlife").team(team).build();
        user3.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user3).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user3).build()
        ));

        User user4 = User.builder().username("bigduck151").password("golfer1").team(team).build();
        user4.setPosts(List.of(
                Post.builder().text("lol kek wut").language(en).user(user4).build(),
                Post.builder().text("лол кек ват").language(ru).user(user4).build()
        ));

        User user5 = User.builder().username("yellowgorilla497").password("talisman").team(team).build();
        user5.setPosts(List.of(
                Post.builder().text("foo bar baz").language(en).user(user5).build(),
                Post.builder().text("фуу бар баз").language(ru).user(user5).build()
        ));

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));

        log.info("--- save finished ---");
    }

    @Transactional
    public void load() {
        log.info("--- load started  ---");
        long start = System.currentTimeMillis();

        PageRequest pageRequest = PageRequest.of(0, 2);
        List<User> users = userRepository.findAllByTeamIdAndPostsLanguageCodeAndPostsTextContaining(
                1L, "ru", "бар", pageRequest).getContent();
        users.forEach(user -> user.getPosts().iterator());

        log.info("--- elapsed: {} ms", System.currentTimeMillis() - start);

        for (User user : users) {
            log.info("user: {}", user.toString());
        }

        log.info("--- load finished ---");
    }
}
