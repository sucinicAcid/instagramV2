package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.db.post.PostEntity;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PostValidator {
    private final PostRepository postRepository;

    public void validate(CreatePost createPost) {
        validateTitle(createPost.title());
        validateContent(createPost.content());
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

    public void validateExist(Long postId) {
        postRepository.findById(postId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 게시물입니다."));
    }

    public void isOwnedByUser(Long postId, Long userId) {
        PostEntity post = postRepository.findById(postId).get();
        if (post.getUserId() != userId) {
            throw new IllegalStateException("본인 게시물만 삭제할 수 있습니다.");
        }
    }

    public void validate(UpdatePost updatePost) {
        validateTitle(updatePost.title());
        validateContent(updatePost.content());
        validateExist(updatePost.postId());
    }

    private void validateTitle(String title) {
        if (title.isBlank()) {
            throw new IllegalStateException("제목에 빈칸이 들어갈 수 없습니다.");
        }
    }

    private void validateContent(String content) {
        if (content.isBlank()) {
            throw new IllegalStateException("내용에 빈칸이 들어갈 수 없습니다.");
        }
    }
}
