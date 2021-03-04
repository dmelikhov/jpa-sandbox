package dimas.jpa.sandbox.user;

import dimas.jpa.sandbox.entity.Post;
import dimas.jpa.sandbox.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByTeamIdAndPostsLanguageCodeAndPostsTextContaining(
            Long teamId,
            String lang,
            String query,
            Pageable pageable);

    @Query("" +
            "select u from User u " +
            "where :postId in (select p.id from u.posts p)")
    List<User> findAllByPostsContaining(Long postId);

    List<User> findAllByPostsId(Long post);

    @Query("" +
            "select u from User u " +
            "where exists (" +
            "  select 1 from u.posts p " +
            "  where p.id in :postIds " +
            ")")
    List<User> findAllByPostsIntersecting(List<Long> postIds);
}
