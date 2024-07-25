package sjs.instagram.domain.follow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.follow.FollowEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowRepositoryTest {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    private FollowEntity createFollow() {
        UserEntity user = userRepository.save(new UserEntity());
        UserEntity target = userRepository.save(new UserEntity());
        FollowEntity follow = new FollowEntity(user.getId(), target.getId());
        return followRepository.save(follow);
    }

    @Test
    @DisplayName("팔로우 생성")
    void create() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        UserEntity target = userRepository.save(new UserEntity());
        FollowEntity follow = new FollowEntity(user.getId(), target.getId());

        //when
        FollowEntity saved = followRepository.save(follow);

        //then
        assertThat(saved).isEqualTo(follow);
    }

    @Test
    @DisplayName("팔로잉 되어 있는지 찾기: 팔로잉 한 경우")
    void isFollowing() {
        //given
        FollowEntity follow = createFollow();
        Long userId = follow.getFromUserId();
        Long targetId = follow.getToUserId();

        //when
        boolean result = followRepository.existsByFromUserIdAndToUserId(userId, targetId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("팔로잉 되어 있는지 찾기: 팔로잉 하지 않은 경우")
    void isNotFollowing() {
        //given
        FollowEntity follow = createFollow();
        Long userId = follow.getFromUserId();
        Long targetId = follow.getToUserId();

        //when
        boolean result1 = followRepository.existsByFromUserIdAndToUserId(userId + targetId, targetId);
        boolean result2 = followRepository.existsByFromUserIdAndToUserId(userId, targetId + userId);
        boolean result3 = followRepository.existsByFromUserIdAndToUserId(userId + targetId, targetId + userId);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isFalse();
    }
}