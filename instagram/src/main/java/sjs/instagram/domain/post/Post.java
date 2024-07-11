package sjs.instagram.domain.post;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "POSTS")
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "POST_IMAGES", joinColumns = @JoinColumn(name = "POST_ID"))
    private List<PostImage> images;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;
}
