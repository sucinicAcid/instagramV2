package sjs.instagram.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.user.UserEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private UserEntity createUser() {
        return new UserEntity();
    }

    @Test
    @DisplayName("사용자 생성")
    void create() {
        //given
        UserEntity user = createUser();

        //when
        UserEntity saved = userRepository.save(user);

        //then
        assertThat(saved)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    @DisplayName("사용자 조회")
    void read() {
        //given
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);

        //when
        UserEntity find = userRepository.findById(saved.getId()).get();

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(find);
    }
}