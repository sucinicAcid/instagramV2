package sjs.instagram.db.story;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STORIES")
@Getter
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STORY_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "STORY_ID")
    private List<StoryImageEntity> images = new ArrayList<>();
}
