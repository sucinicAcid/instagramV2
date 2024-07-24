package sjs.instagram.domain.post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreatePost(
        String title,
        String content,
        List<MultipartFile> uploadFiles
) {
}
