package sjs.instagram.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.user.JoinUser;
import sjs.instagram.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(find.getRole()).isEqualTo(UserEntity.UserRole.ROLE_USER);
        assertThat(find.getPrivacy()).isEqualTo(UserEntity.UserAccountPrivacy.PUBLIC);
    }

    @Test
    @DisplayName("회원 가입 실패: 아이디가 6자 미만이거나 15자 초과")
    void failJoinByIdLength() {
        //given
        JoinUser user1 = new JoinUser("id", "pw123456");
        JoinUser user2 = new JoinUser("id123456789101112131415", "pw1234567");

        //when then
        assertThatThrownBy(() -> userService.joinUser(user1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아이디는 6자 이상 15자 이하여야 합니다.");
        assertThatThrownBy(() -> userService.joinUser(user2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아이디는 6자 이상 15자 이하여야 합니다.");
    }

    @Test
    @DisplayName("회원 가입 실패: 아이디는 영문 소문자,대문자,숫자만 사용 가능")
    void failJoinByIdCharacter() {
        //given
        JoinUser user = new JoinUser("id!@#$%^*", "pw123456");

        //when then
        assertThatThrownBy(() -> userService.joinUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아이디는 영문 소문자,대문자,숫자만 사용 가능합니다.");
    }

    @Test
    @DisplayName("회원 가입 실패: 비밀번호가 6자 미만이거나 15자 초과")
    void failJoinByPasswordLength() {
        //given
        JoinUser user1 = new JoinUser("id123456", "pw");
        JoinUser user2 = new JoinUser("id123456", "pw123456789101112131415");

        //when then
        assertThatThrownBy(() -> userService.joinUser(user1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("비밀번호는 6자 이상 15자 이하여야 합니다.");
        assertThatThrownBy(() -> userService.joinUser(user2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("비밀번호는 6자 이상 15자 이하여야 합니다.");
    }

    @Test
    @DisplayName("회원 가입 실패: 비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능")
    void failJoinByPasswordCharacter() {
        //given
        JoinUser user = new JoinUser("id123456", "pw12@#$%");

        //when then
        assertThatThrownBy(() -> userService.joinUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능합니다.");
    }
}