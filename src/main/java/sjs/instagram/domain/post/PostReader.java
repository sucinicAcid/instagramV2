package sjs.instagram.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sjs.instagram.domain.comment.CommentDetails;
import sjs.instagram.domain.comment.CommentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<ThumbnailPost> readThumbnailPost(Long userId) {
        return postRepository.findThumbnailPost(userId);
    }

    public Long readCount(Long userId) {
        return postRepository.countByUserId(userId);
    }

    public PostDetails readPostDetails(Long postId) {
        PostDetails postDetails = postRepository.findPostDetails(postId);
        List<String> postImages = postRepository.findStoreFileName(postId);
        List<CommentDetails> commentDetails = commentRepository.findCommentDetails(postId);

        return new PostDetails(
                postDetails.name(),
                postDetails.userImage(),
                postImages,
                postDetails.title(),
                postDetails.content(),
                postDetails.likes(),
                commentDetails);
    }
}
