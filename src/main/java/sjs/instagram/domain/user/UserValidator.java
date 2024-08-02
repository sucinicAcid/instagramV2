package sjs.instagram.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.exception.NoUserFoundException;
import sjs.instagram.domain.exception.ValidationError;
import sjs.instagram.domain.exception.LoginIdPasswordInvalidException;
import sjs.instagram.domain.follow.FollowRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void validateExist(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NoUserFoundException("존재하지 않는 사용자입니다."));
    }

    public void canViewPost(Long userId, Long targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId).get();
        if (targetUser.getPrivacy() == UserEntity.UserAccountPrivacy.PRIVATE)
            if (!followRepository.existsByFromUserIdAndToUserId(userId, targetUserId))
                throw new IllegalStateException("게시물 접근 권한이 없습니다.");
    }

    public void validate(JoinUser joinUser) {
        List<ValidationError> errors = new ArrayList<>();
        String id = joinUser.instagramId();
        String pw = joinUser.password();
        if (id.length() < 6 || id.length() > 15)
            errors.add(new ValidationError("instagramId","아이디는 6자 이상 15자 이하여야 합니다."));
        if (!id.matches("[a-zA-Z0-9]+"))
            errors.add(new ValidationError("instagramId","아이디는 영문 소문자,대문자,숫자만 사용 가능합니다."));
        if (pw.length() < 6 || pw.length() > 15)
            errors.add(new ValidationError("password","비밀번호는 6자 이상 15자 이하여야 합니다."));
        if (!pw.matches("[a-zA-Z0-9?!*]+"))
            errors.add(new ValidationError("password","비밀번호는 영문 소문자,대문자,숫자,?,!,*만 사용 가능합니다."));
        if (!errors.isEmpty())
            throw new LoginIdPasswordInvalidException(errors);
    }

    public void isSameUser(Long userId, Long targetUserId) {
        if (userId != targetUserId)
            throw new IllegalStateException("본인 이외의 계정은 탈퇴가 불가능합니다.");
    }
}
