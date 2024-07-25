package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sjs.instagram.db.post.PostImageEntity;
import sjs.instagram.domain.filestore.FileStore;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostImageFileStore {
    private final FileStore fileStore;

    public List<PostImageEntity> store(List<MultipartFile> uploadFiles) {
        return uploadFiles.stream().map(this::store).collect(Collectors.toList());
    }

    private PostImageEntity store(MultipartFile uploadFile) {
        String uploadFileName = uploadFile.getOriginalFilename();
        String storeFileName = fileStore.store(uploadFile);
        return new PostImageEntity(uploadFileName, storeFileName);
    }
}
