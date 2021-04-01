package dimas.jpa.sandbox.service;

import dimas.jpa.sandbox.domain.post.PostRepository;
import dimas.jpa.sandbox.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        log.info("UserService.init");
        userService.save();
        userService.load();
    }

    @Transactional
    public void save() {
        log.info("UserService.save");
    }

    @Transactional
    public void load() {
        log.info("UserService.load");
        userRepository.findAll();
    }
}
