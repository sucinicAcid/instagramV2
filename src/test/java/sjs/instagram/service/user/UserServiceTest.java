package sjs.instagram.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.exception.LoginIdPasswordInvalidException;
import sjs.instagram.domain.exception.NoUserFoundException;
import sjs.instagram.domain.exception.ValidationError;
import sjs.instagram.domain.user.JoinUser;
import sjs.instagram.domain.user.UserInfo;
import sjs.instagram.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatList;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입")
    void join() {
        //given
        JoinUser joinUser = new JoinUser("id123456", "pw123?!*");

        //when
        Long userId = userService.joinUser(joinUser);

        //given
        UserEntity find = userRepository.findById(userId).get();
        assertThat(find.getId()).isEqualTo(userId);
        assertThat(find.getInstagramId()).isEqualTo(joinUser.instagramId());
        assertThat(passwordEncoder.matches(joinUser.password(), find.getPassword())).isTrue();
        assertThat(find.getName()).isEqualTo(" ");
        assertThat(find.getIntroduction()).isEqualTo(" ");
        assertThat(find.getImage().getUploadFileName()).isEqualTo("defaultUserImage.jpg");
        assertThat(find.getImage().getStoreFileName()).isEqualTo("defaultUserImage.jpg");
        assertThat(find.getRole()).isEqualTo(UserEntity.UserRole.USER);
        assertThat(find.getPrivacy()).isEqualTo(UserEntity.UserAccountPrivacy.PUBLIC);
    }

    @Test
    @DisplayName("회원 가입 실패: 아이디가 6자 미만이거나 15자 초과")
    void failJoinByIdLength() {
        //given
        JoinUser user1 = new JoinUser("id", "pw123456");
        JoinUser user2 = new JoinUser("id123456789101112131415", "pw1234567");

        //when then
        LoginIdPasswordInvalidException ex1 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user1)
        );
        assertThatList(ex1.getErrors()).hasSize(1);
        assertThatList(ex1.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 6자 이상 15자 이하여야 합니다."));

        LoginIdPasswordInvalidException ex2 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user2)
        );
        assertThatList(ex2.getErrors()).hasSize(1);
        assertThatList(ex2.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 6자 이상 15자 이하여야 합니다."));
    }

    @Test
    @DisplayName("회원 가입 실패: 아이디는 영문 소문자,대문자,숫자만 사용 가능")
    void failJoinByIdCharacter() {
        //given
        JoinUser user = new JoinUser("id!@#$%^*", "pw123456");

        //when then
        LoginIdPasswordInvalidException ex = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user)
        );
        assertThatList(ex.getErrors()).hasSize(1);
        assertThatList(ex.getErrors())
                .containsOnly(new ValidationError("instagramId", "아이디는 영문 소문자,대문자,숫자만 사용 가능합니다."));
    }

    @Test
    @DisplayName("회원 가입 실패: 비밀번호가 6자 미만이거나 15자 초과")
    void failJoinByPasswordLength() {
        //given
        JoinUser user1 = new JoinUser("id123456", "pw");
        JoinUser user2 = new JoinUser("id123456", "pw123456789101112131415");

        //when then
        LoginIdPasswordInvalidException ex1 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user1)
        );
        assertThatList(ex1.getErrors()).hasSize(1);
        assertThatList(ex1.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 6자 이상 15자 이하여야 합니다."));

        LoginIdPasswordInvalidException ex2 = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user2)
        );
        assertThatList(ex2.getErrors()).hasSize(1);
        assertThatList(ex2.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 6자 이상 15자 이하여야 합니다."));
    }

    @Test
    @DisplayName("회원 가입 실패: 비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능")
    void failJoinByPasswordCharacter() {
        //given
        JoinUser user = new JoinUser("id123456", "pw12@#$%");

        //when then
        LoginIdPasswordInvalidException ex = Assertions.assertThrows(
                LoginIdPasswordInvalidException.class,
                () -> userService.joinUser(user)
        );
        assertThatList(ex.getErrors()).hasSize(1);
        assertThatList(ex.getErrors())
                .containsOnly(new ValidationError("password", "비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능합니다."));
    }

    @Test
    @DisplayName("회원 탈퇴")
    void removeUser() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when
        userService.removeUser(saved.getId(), saved.getId());

        //then
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("회원 탈퇴 실패: 본인의 계정만 탈퇴할 수 있음")
    void failRemoveUser() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when
        assertThatThrownBy(() ->
                userService.removeUser(saved.getId(), saved.getId()+1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 이외의 계정은 탈퇴가 불가능합니다.");
    }

    @Test
    @DisplayName("사용자 정보 조회")
    void readUserInfo() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        userRepository.save(user);

        //when
        UserInfo info = userService.readUserInfo(user.getId());

        //then
        assertThat(info.instagramId()).isEqualTo(user.getInstagramId());
        assertThat(info.name()).isEqualTo(user.getName());
        assertThat(info.introduction()).isEqualTo(user.getIntroduction());
        assertThat(info.privacy()).isEqualTo(user.getPrivacy());
    }

    @Test
    @DisplayName("사용자 정보 조회 실패: 존재하지 않는 사용자")
    void failReadUserInfo() {
        assertThatThrownBy(() ->
                userService.readUserInfo(1L)).
                isInstanceOf(NoUserFoundException.class).
                hasMessage("존재하지 않는 사용자입니다.");
    }
}