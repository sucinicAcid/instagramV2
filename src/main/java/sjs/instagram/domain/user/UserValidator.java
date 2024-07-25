package sjs.instagram.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.follow.FollowRepository;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void validateExist(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 사용자입니다."));
    }

    public void canViewPost(Long userId, Long targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId).get();
        if (targetUser.getPrivacy() == UserEntity.UserAccountPrivacy.PRIVATE)
            if (!followRepository.existsByFromUserIdAndToUserId(userId, targetUserId))
                throw new IllegalStateException("게시물 접근 권한이 없습니다.");
    }
}
