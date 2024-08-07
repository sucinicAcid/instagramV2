package sjs.instagram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sjs.instagram.db.comment.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("""
    SELECT new sjs.instagram.domain.comment.CommentDetails(
        (SELECT u.instagramId FROM UserEntity u WHERE u.id = c.userId),
        (SELECT u.image.storeFileName FROM UserEntity u WHERE u.id = c.userId),
        c.content,
        (SELECT COUNT(cl) FROM CommentLikeEntity cl WHERE cl.commentId = c.id)
    )
    FROM CommentEntity c
    WHERE c.postId = :postId
    """)
    List<CommentDetails> findCommentDetails(@Param("postId") Long postId);
}
