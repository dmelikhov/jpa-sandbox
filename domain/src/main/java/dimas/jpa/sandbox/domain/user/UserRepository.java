package dimas.jpa.sandbox.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    User findByUsername(String username);
}
