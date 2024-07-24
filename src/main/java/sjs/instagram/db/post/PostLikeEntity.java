package sjs.instagram.db.post;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "POST_LIKES")
@Getter
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_LIKE_ID")
    private Long id;

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "USER_ID")
    private Long userId;
}
