package dimas.jpa.sandbox.controller;

import dimas.jpa.sandbox.domain.user.User;
import dimas.jpa.sandbox.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
