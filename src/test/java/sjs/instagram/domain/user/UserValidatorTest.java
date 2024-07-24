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
class UserValidatorTest {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserRepository userRepository;

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
}