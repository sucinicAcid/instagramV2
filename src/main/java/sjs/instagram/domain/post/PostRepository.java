package sjs.instagram.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sjs.instagram.db.post.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "select new sjs.instagram.domain.post.ThumbnailPost(p.id, i.image.storeFileName) " +
            "from PostEntity p " +
            "join p.images i " +
            "where p.userId = :userId " +
            "and i = (select ii from PostEntity pp join pp.images ii where p = pp order by ii.createdDate limit 1)" +
            "order by p.createdDate"
    )
    List<ThumbnailPost> findThumbnailPost(@Param("userId") Long userId);

    Long countByUserId(Long userId);
}
