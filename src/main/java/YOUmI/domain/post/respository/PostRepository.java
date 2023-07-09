package YOUmI.domain.post.respository;

import YOUmI.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p " +
            " from Post p " +
            "where p.postNo = :postNo" +
            "  and p.deleteYn = false ")
    Optional<Post> findFirstByPostNo(int postNo);
}
