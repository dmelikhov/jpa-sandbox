package dimas.jpa.sandbox.user;

import dimas.jpa.sandbox.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
