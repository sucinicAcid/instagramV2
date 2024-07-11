package sjs.instagram.domain.story;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "STORIES")
@Getter
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STORY_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Embedded
    private StoryImage image;
}
