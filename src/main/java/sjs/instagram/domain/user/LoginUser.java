package sjs.instagram.domain.user;

import sjs.instagram.db.user.UserEntity;

public record LoginUser(
        Long id,
        String instagramId,
        String password,
        UserEntity.UserRole role
) {
}
