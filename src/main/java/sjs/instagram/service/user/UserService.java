package sjs.instagram.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.user.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserProcessor userProcessor;
    private final UserValidator userValidator;
    private final UserReader userReader;

    public Long joinUser(JoinUser joinUser) {
        userValidator.validate(joinUser);
        return userProcessor.append(joinUser);
    }

    public void removeUser(Long userId, Long targetUserId) {
        userValidator.isSameUser(userId, targetUserId);
        userProcessor.remove(targetUserId);
    }

    public UserInfo readUserInfo(Long userId) {
        userValidator.validateExist(userId);
        return userReader.read(userId);
    }

    public Long read(String instagramId) {
        return userReader.readByInstagramId(instagramId);
    }
}
