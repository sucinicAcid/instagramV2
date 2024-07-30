package sjs.instagram.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.user.JoinUser;
import sjs.instagram.domain.user.UserProcessor;
import sjs.instagram.domain.user.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserProcessor userProcessor;
    private final UserValidator userValidator;

    public Long joinUser(JoinUser joinUser) {
        userValidator.validate(joinUser);
        return userProcessor.append(joinUser);
    }
}
