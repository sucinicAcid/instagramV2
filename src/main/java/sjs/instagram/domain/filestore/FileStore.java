package sjs.instagram.domain.filestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String store(MultipartFile uploadFile){
        if (uploadFile.isEmpty()) {
            throw new IllegalStateException("빈 파일은 사용할 수 없습니다.");
        }
        String uploadFileName = uploadFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        String storeFileFullPath = fileDir + storeFileName;
        try {
            uploadFile.transferTo(new File(storeFileFullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return storeFileName;
    }

    private String createStoreFileName(String uploadFileName) {
        String ext = extractExt(uploadFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(pos + 1);
    }

    public String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }
}
