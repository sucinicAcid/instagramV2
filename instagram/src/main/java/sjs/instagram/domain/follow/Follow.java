package sjs.instagram.domain.follow;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOLLOWS")
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FOLLOW_ID")
    private Long id;

    @Column(name = "FROM_USER_ID")
    private Long fromUserId;

    @Column(name = "TO_USER_ID")
    private Long toUserId;
}
