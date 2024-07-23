package sjs.instagram.db.story;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "STORY_LIKES")
@Getter
public class StoryLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STORY_LIKE_ID")
    private Long id;

    @Column(name = "STORY_ID")
    private Long storyId;

    @Column(name = "USER_ID")
    private Long userId;
}
