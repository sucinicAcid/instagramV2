package sjs.instagram.domain.story;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "STORY_VIEWS")
@Getter
public class StoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STORY_VIEW_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "STORY_ID")
    private Long storyId;
}
