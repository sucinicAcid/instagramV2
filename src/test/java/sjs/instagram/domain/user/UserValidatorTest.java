package sjs.instagram.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.follow.FollowEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.follow.FollowRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserValidatorTest {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

    @Test
    @DisplayName("사용자 존재 검증")
    void validateExist() {
        //given
        UserEntity user = new UserEntity();
        UserEntity saved = userRepository.save(user);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.validateExist(saved.getId()));
    }

    @Test
    @DisplayName("사용자 존재 검증 오류")
    void failValidateExist() {
        assertThatThrownBy(() -> userValidator.validateExist(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("게시물 보기 접근 권한 검증: 팔로우 관계일 때")
    void validateCanViewPostWhenFollowing() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        UserEntity target = userRepository.save(new UserEntity());
        FollowEntity follow = new FollowEntity(user.getId(), target.getId());
        followRepository.save(follow);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.canViewPost(user.getId(), target.getId()));
    }

    @Test
    @DisplayName("게시물 보기 접근 권한 검증: 상대 계정이 PUBLIC일 때")
    void validateCanViewPostWhenTargetAccountIsPUBLIC() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        UserEntity target = userRepository.save(new UserEntity());
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PUBLIC);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.canViewPost(user.getId(), target.getId()));
    }

    @Test
    @DisplayName("게시물 보기 접근 권한 검증 오류: 팔로잉 하지 않으면서 상대 계정이 PRIVATE일 때")
    void failValidateCanViewPostByInvalidAccess() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        UserEntity target = userRepository.save(new UserEntity());
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PRIVATE);

        //when then
        assertThatThrownBy(() -> userValidator.canViewPost(user.getId(), target.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게시물 접근 권한이 없습니다.");
    }
}