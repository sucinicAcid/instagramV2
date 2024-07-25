package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.post.PostImageEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostProcessor {
    private final PostRepository postRepository;
    private final PostImageFileStore postImageFileStore;

    public Long post(Long userId, CreatePost createPost) {
        List<PostImageEntity> images = postImageFileStore.store(createPost.uploadFiles());
        PostEntity saved = postRepository.save(
                new PostEntity(
                        userId,
                        images,
                        createPost.title(),
                        createPost.content()
                )
        );
        return saved.getId();
    }

    public void remove(Long postId) {
        postRepository.deleteById(postId);
    }

    public void update(UpdatePost updatePost) {
        PostEntity post = postRepository.findById(updatePost.postId()).get();
        post.update(updatePost.title(), updatePost.content());
    }
}
