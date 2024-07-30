package sjs.instagram.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.user.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserReaderTest {
    @Autowired
    private UserReader userReader;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 정보 조회")
    void read() {
        //given
        UserEntity user = new UserEntity("id123456", "pw123456");
        userRepository.save(user);

        //when
        UserInfo info = userReader.read(user.getId());

        //then
        assertThat(info.instagramId()).isEqualTo(user.getInstagramId());
        assertThat(info.name()).isEqualTo(user.getName());
        assertThat(info.introduction()).isEqualTo(user.getIntroduction());
        assertThat(info.privacy()).isEqualTo(user.getPrivacy());
    }
}