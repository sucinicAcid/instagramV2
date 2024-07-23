package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.domain.user.UserRepository;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PostValidator {
    private final UserRepository userRepository;

    public void validate(CreatePost createPost) {
        if (createPost.title().isBlank()) {
            throw new IllegalStateException("제목에 빈칸이 들어갈 수 없습니다.");
        }
        if (createPost.content().isBlank()) {
            throw new IllegalStateException("내용에 빈칸이 들어갈 수 없습니다.");
        }
        if (createPost.uploadFiles().size() == 0) {
            throw new IllegalStateException("사진은 최소 1장 게시해야 합니다.");
        }
        createPost.uploadFiles().
                stream().
                filter(MultipartFile::isEmpty).
                forEach(uploadFile -> {
            throw new IllegalStateException("빈 파일은 사용할 수 없습니다.");
        });
    }
}
