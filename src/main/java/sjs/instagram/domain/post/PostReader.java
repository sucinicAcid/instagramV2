package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;

    public List<ThumbnailPost> readThumbnailPost(Long userId) {
        return postRepository.findThumbnailPost(userId);
    }
}
