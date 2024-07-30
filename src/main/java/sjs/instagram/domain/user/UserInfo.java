package sjs.instagram.domain.user;

import sjs.instagram.db.user.UserEntity;

public record UserInfo(
        String instagramId,
        String name,
        String introduction,
        UserEntity.UserAccountPrivacy privacy
) {
    public UserInfo(String instagramId, String name, String introduction, UserEntity.UserAccountPrivacy privacy) {
        this.instagramId = instagramId;
        this.name = name;
        this.introduction = introduction;
        this.privacy = privacy;
    }
}
