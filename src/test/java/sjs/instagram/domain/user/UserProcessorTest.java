package sjs.instagram.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.db.user.UserImage;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserProcessorTest {
    @Autowired
    private UserProcessor userProcessor;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입")
    void append() {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");

        //when
        Long userId = userProcessor.append(user);

        //given
        UserEntity find = userRepository.findById(userId).get();
        assertThat(find.getId()).isEqualTo(userId);
        assertThat(find.getInstagramId()).isEqualTo(user.instagramId());
        assertThat(passwordEncoder.matches(user.password(), find.getPassword())).isTrue();
        assertThat(find.getName()).isEqualTo(" ");
        assertThat(find.getIntroduction()).isEqualTo(" ");
        assertThat(find.getImage().getUploadFileName()).isEqualTo("defaultUserImage.jpg");
        assertThat(find.getImage().getStoreFileName()).isEqualTo("defaultUserImage.jpg");
        assertThat(find.getRole()).isEqualTo(UserEntity.UserRole.USER);
        assertThat(find.getPrivacy()).isEqualTo(UserEntity.UserAccountPrivacy.PUBLIC);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void remove() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        UserEntity saved = userRepository.save(user);

        //when
        userProcessor.remove(saved.getId());

        //then
        assertThat(userRepository.findAll()).isEmpty();
    }
}