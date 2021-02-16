package dimas.jpa.sandbox.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user", "user.posts"})
    Page<Post> findAllByUserGroupIdAndLanguageCodeAndTextContaining(
            Long groupId, String lang, String query, Pageable pageable);
}
