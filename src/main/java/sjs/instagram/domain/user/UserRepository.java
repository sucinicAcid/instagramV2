package sjs.instagram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sjs.instagram.db.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
    select new sjs.instagram.domain.user.UserInfo(
        u.instagramId, 
        u.name, 
        u.introduction, 
        u.image.storeFileName, 
        u.privacy, 
        (select count(f1) from FollowEntity f1 where f1.toUserId = :userId), 
        (select count(f2) from FollowEntity f2 where f2.fromUserId = :userId)
    ) 
    from UserEntity u 
    where u.id = :userId
    """)
    UserInfo findUserInfo(@Param("userId") Long userId);

    UserEntity findByInstagramId(String instagramId);
}
