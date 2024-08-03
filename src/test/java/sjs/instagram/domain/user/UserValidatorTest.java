package sjs.instagram.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.follow.FollowEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.exception.CannotViewPostException;
import sjs.instagram.domain.exception.LoginIdPasswordInvalidException;
import sjs.instagram.domain.exception.NoUserFoundException;
import sjs.instagram.domain.exception.ValidationError;
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
        UserEntity user = new UserEntity("id1", "pw1");
        UserEntity saved = userRepository.save(user);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.validateExist(saved.getId()));
    }

    @Test
    @DisplayName("사용자 존재 검증 오류")
    void failValidateExist() {
        assertThatThrownBy(() -> userValidator.validateExist(1L))
                .isInstanceOf(NoUserFoundException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("게시물 보기 접근 권한 검증: 팔로우 관계일 때")
    void validateCanViewPostWhenFollowing() {
        //given
        UserEntity user = userRepository.save(new UserEntity("id1", "pw1"));
        UserEntity target = userRepository.save(new UserEntity("id2", "pw2"));
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
        UserEntity user = userRepository.save(new UserEntity("id1", "pw1"));
        UserEntity target = userRepository.save(new UserEntity("id2", "pw2"));
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PUBLIC);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.canViewPost(user.getId(), target.getId()));
    }

    @Test
    @DisplayName("게시물 보기 접근 권한 검증 오류: 팔로잉 하지 않으면서 상대 계정이 PRIVATE일 때")
    void failValidateCanViewPostByInvalidAccess() {
        //given
        UserEntity user = userRepository.save(new UserEntity("id1", "pw1"));
        UserEntity target = userRepository.save(new UserEntity("id2", "pw2"));
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PRIVATE);

        //when then
        assertThatThrownBy(() -> userValidator.canViewPost(user.getId(), target.getId()))
                .isInstanceOf(CannotViewPostException.class)
                .hasMessage("게시물 접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("아이디 길이 검증")
    void validateIdLength() {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");

        //when then
        assertThatNoException().isThrownBy(() -> userValidator.validate(user));
    }

    @Test
    @DisplayName("아이디 길이 검증 실패: 아이디가 6자 미만이거나 15자 초과")
    void failValidateIdLength() {
        //given
        JoinUser user1 = new JoinUser("id", "pw123456");
        JoinUser user2 = new JoinUser("id123456789101112131415", "pw1234567");

        //when then
        LoginIdPasswordInvalidException ex1 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user1)
        );
        assertThatList(ex1.getErrors()).hasSize(1);
        assertThatList(ex1.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 6자 이상 15자 이하여야 합니다."));

        LoginIdPasswordInvalidException ex2 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user2)
        );
        assertThatList(ex2.getErrors()).hasSize(1);
        assertThatList(ex2.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 6자 이상 15자 이하여야 합니다."));
    }

    @Test
    @DisplayName("아이디 유효문자 검증")
    void validateIdCharacter() {
        //given
        JoinUser user = new JoinUser("idID1234", "pw123456");

        //when then
        assertThatNoException().isThrownBy(() -> userValidator.validate(user));
    }

    @Test
    @DisplayName("아이디 유효문자 검증 실패: 아이디는 영문 소문자,대문자,숫자만 사용 가능")
    void failValidateIdCharacter() {
        //given
        JoinUser user = new JoinUser("id!@#$%^*", "pw123456");

        //when then
        LoginIdPasswordInvalidException ex = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user)
        );
        assertThatList(ex.getErrors()).hasSize(1);
        assertThatList(ex.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 영문 소문자,대문자,숫자만 사용 가능합니다."));
    }

    @Test
    @DisplayName("비밀번호 길이 검증")
    void validatePasswordLength() {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");

        //when then
        assertThatNoException().isThrownBy(() -> userValidator.validate(user));
    }

    @Test
    @DisplayName("비밀번호 길이 검증 실패: 비밀번호가 6자 미만이거나 15자 초과")
    void failValidatePasswordLength() {
        //given
        JoinUser user1 = new JoinUser("id123456", "pw");
        JoinUser user2 = new JoinUser("id123456", "pw123456789101112131415");

        //when then
        LoginIdPasswordInvalidException ex1 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user1)
        );
        assertThatList(ex1.getErrors()).hasSize(1);
        assertThatList(ex1.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 6자 이상 15자 이하여야 합니다."));

        LoginIdPasswordInvalidException ex2 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user2)
        );
        assertThatList(ex2.getErrors()).hasSize(1);
        assertThatList(ex2.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 6자 이상 15자 이하여야 합니다."));
    }

    @Test
    @DisplayName("비밀번호 유효문자 검증")
    void validatePasswordCharacter() {
        //given
        JoinUser user = new JoinUser("id123456", "pwPW1!?*");

        //when then
        assertThatNoException().isThrownBy(() -> userValidator.validate(user));
    }

    @Test
    @DisplayName("비밀번호 유효문자 검증 실패: 비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능")
    void failValidatePasswordCharacter() {
        //given
        JoinUser user = new JoinUser("id123456", "pw12@#$%");

        //when then
        LoginIdPasswordInvalidException ex = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userValidator.validate(user)
        );
        assertThatList(ex.getErrors()).hasSize(1);
        assertThatList(ex.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능합니다."));
    }

    @Test
    @DisplayName("같은 사용자인지 검증")
    void validateSameUser() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.isSameUser(saved.getId(), saved.getId()));
    }

    @Test
    @DisplayName("같은 사용자인지 검증 실패")
    void failValidateSameUser() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when then
        assertThatThrownBy(() ->
                userValidator.isSameUser(saved.getId(), saved.getId()+1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 이외의 계정은 탈퇴가 불가능합니다.");
    }

    @Test
    @DisplayName("인스타그램 아이디로 사용자 존재하는지 검증")
    void validateExistByInstagramId() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when then
        assertThatNoException().isThrownBy(() ->
                userValidator.validateExist("id123456")
        );
    }

    @Test
    @DisplayName("인스타그램 아이디로 사용자 존재하는지 검증 실패")
    void failValidateExistByInstagramId() {
        assertThatThrownBy(() -> userValidator.validateExist("id123456"))
                .isInstanceOf(NoUserFoundException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }
}