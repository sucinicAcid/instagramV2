package sjs.instagram.db.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sjs.instagram.db.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POSTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "POST_ID")
    private List<PostImageEntity> images = new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    public PostEntity(Long userId, List<PostImageEntity> images, String title, String content) {
        this.userId = userId;
        this.images = images;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
