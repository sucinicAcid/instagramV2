package sjs.instagram.domain.user;

import sjs.instagram.db.user.UserEntity;

public record UserInfo(
        String instagramId,
        String name,
        String introduction,
        String imageStoreFileName,
        UserEntity.UserAccountPrivacy privacy,
        Long followerCount,
        Long followingCount
) {
    public UserInfo(String instagramId,
                    String name,
                    String introduction,
                    String imageStoreFileName,
                    UserEntity.UserAccountPrivacy privacy,
                    Long followerCount,
                    Long followingCount) {
        this.instagramId = instagramId;
        this.name = name;
        this.introduction = introduction;
        this.imageStoreFileName = imageStoreFileName;
        this.privacy = privacy;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
}
