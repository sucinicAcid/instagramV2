package sjs.instagram.db.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "USERS")
@Getter
public class UserEntity {
    public enum UserRole { ROLE_USER, ROLE_ADMIN }
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
    private UserAccountPrivacy privacy = UserAccountPrivacy.PUBLIC;

    public void changePrivacyTo(UserAccountPrivacy privacy) {
        this.privacy = privacy;
    }
}
