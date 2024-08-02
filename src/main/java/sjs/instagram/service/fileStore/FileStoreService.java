package sjs.instagram.service.fileStore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.filestore.FileStore;

@Service
@Transactional
@RequiredArgsConstructor
public class FileStoreService {
    private final FileStore fileStore;

    public String getFullPath(String storeFileName) {
        return fileStore.getFullPath(storeFileName);
    }
}
