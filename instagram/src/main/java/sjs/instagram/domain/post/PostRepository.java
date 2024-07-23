package sjs.instagram.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import sjs.instagram.db.post.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
