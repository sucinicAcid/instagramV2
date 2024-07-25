package sjs.instagram.db.story;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sjs.instagram.db.post.PostImage;

@Entity
@Table(name = "STORY_IMAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Embedded
    StoryImage image;
}
