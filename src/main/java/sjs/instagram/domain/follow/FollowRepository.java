package sjs.instagram.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import sjs.instagram.db.follow.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
