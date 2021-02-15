package dimas.jpa.sandbox.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

//    @EntityGraph(attributePaths = "posts")
//    List<User> findAllByPostsLanguageCodeAndPostsTextContaining(String lang, String query);

//    @Query("select u from User u join u.posts p where p.language.code = :lang and p.text like %:query%")
//    @EntityGraph(attributePaths = "posts")
//    List<User> findAllByLanguageAndQuery(String lang, String query);

//    @Query(
//            value = "" +
//                    "select distinct u " +
//                    "from User u " +
//                    "join fetch u.posts fp " +
//                    "join fetch fp.language fl " +
//                    "join u.posts p " +
//                    "where p.language.code = :lang " +
//                    "  and p.text like %:#{escape(#query)}% escape :#{escapeCharacter()}",
//            countQuery = "" +
//                    "select count(u) " +
//                    "from User u " +
//                    "join u.posts p " +
//                    "where p.language.code = :lang " +
//                    "  and p.text like %:#{escape(#query)}% escape :#{escapeCharacter()}")
//    @QueryHints(@QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
//    Page<User> findAllByLanguageAndQuery(String lang, String query, Pageable pageable);

    @Query("" +
            "select u.id " +
            "from User u " +
            "join u.posts p " +
            "where p.language.code = :lang " +
            "  and p.text like %:#{escape(#query)}% escape :#{escapeCharacter()}")
    Page<Long> findIdsByLanguageAndQuery(String lang, String query, Pageable pageable);

    @EntityGraph(attributePaths = {"posts", "posts.language"})
    List<User> findAllWithPostsByIdIn(List<Long> ids);

    default Page<User> findAllByLanguageAndQuery(String lang, String query, Pageable pageable) {
        Page<Long> idsPage = findIdsByLanguageAndQuery(lang, query, pageable);
        List<User> users = findAllWithPostsByIdIn(idsPage.getContent());
        return new PageImpl<>(users, pageable, idsPage.getTotalElements());
    }
}
