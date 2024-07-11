package sjs.instagram.domain.comment;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "COMMENTS")
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "CONTENT")
    private String content;
}
