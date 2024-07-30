package sjs.instagram.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sjs.instagram.db.user.UserEntity;

@Component
@RequiredArgsConstructor
public class UserProcessor {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Long append(JoinUser joinUser) {
        String rawPassword = joinUser.password();
        String encPassword = passwordEncoder.encode(rawPassword);
        UserEntity user = new UserEntity(joinUser.instagramId(), encPassword);
        UserEntity saved = userRepository.save(user);
        return saved.getId();
    }
}
