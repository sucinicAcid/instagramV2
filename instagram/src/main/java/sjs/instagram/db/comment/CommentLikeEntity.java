package sjs.instagram.db.comment;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "COMMENT_LIKES")
@Getter
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_LIKE_ID")
    private Long id;

    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(name = "USER_ID")
    private Long userId;
}
