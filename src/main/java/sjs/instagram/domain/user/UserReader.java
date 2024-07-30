package sjs.instagram.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public UserInfo read(Long userId) {
        return userRepository.findUserInfo(userId);
    }
}
