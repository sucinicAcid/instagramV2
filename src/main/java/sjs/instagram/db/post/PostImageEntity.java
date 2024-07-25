package sjs.instagram.db.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sjs.instagram.db.base.BaseEntity;

@Entity
@Table(name = "POST_IMAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Embedded
    PostImage image;

    public PostImageEntity(String uploadFileName, String storeFileName) {
        this.image = new PostImage(uploadFileName, storeFileName);
    }
}
