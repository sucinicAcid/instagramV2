package sjs.instagram.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateExist(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 사용자입니다."));
    }
}
