package sjs.instagram.db.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @AllArgsConstructor
    public enum UserRole {
        USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
        public final String fullName;
    }
    public enum UserAccountPrivacy {PUBLIC, PRIVATE}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Embedded
    private UserImage image;

    @Column(name = "INSTAGRAM_ID")
    private String instagramId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INTRODUCTION")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIVACY")
    private UserAccountPrivacy privacy;

    public UserEntity(String instagramId, String password) {
        this.instagramId = instagramId;
        this.password = password;
        this.name = " ";
        this.introduction = " ";
        this.role = UserRole.USER;
        this.privacy = UserAccountPrivacy.PUBLIC;
        this.image = new UserImage();
    }

    public void changePrivacyTo(UserAccountPrivacy privacy) {
        this.privacy = privacy;
    }
}
