package sjs.instagram.db.follow;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOLLOWS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FOLLOW_ID")
    private Long id;

    @Column(name = "FROM_USER_ID")
    private Long fromUserId;

    @Column(name = "TO_USER_ID")
    private Long toUserId;

    public FollowEntity(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
