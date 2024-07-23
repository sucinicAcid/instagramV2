package sjs.instagram.db.post;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
    @Column(name = "UPLOAD_FILE_NAME")
    private String uploadFileName;

    @Column(name = "STORE_FILE_NAME")
    private String storeFileName;

    public PostImage(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
