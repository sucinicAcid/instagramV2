package sjs.instagram.db.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
public class UserImage {
    @Column(name = "UPLOAD_FILE_NAME")
    private String uploadFileName;

    @Column(name = "STORE_FILE_NAME")
    private String storeFileName;

    public UserImage() {
        this.uploadFileName = this.storeFileName = "defaultUserImage.jpg";
    }
}
