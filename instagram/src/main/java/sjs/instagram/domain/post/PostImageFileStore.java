package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sjs.instagram.db.post.PostImage;
import sjs.instagram.domain.filestore.FileStore;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostImageFileStore {
    private final FileStore fileStore;

    public List<PostImage> store(List<MultipartFile> uploadFiles) {
        return uploadFiles.stream().map(this::store).collect(Collectors.toList());
    }

    private PostImage store(MultipartFile uploadFile) {
        String uploadFileName = uploadFile.getOriginalFilename();
        String storeFileName = fileStore.store(uploadFile);
        return new PostImage(uploadFileName, storeFileName);
    }
}
