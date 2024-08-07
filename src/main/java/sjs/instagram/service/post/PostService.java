package sjs.instagram.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.post.*;
import sjs.instagram.domain.user.UserValidator;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostProcessor postProcessor;
    private final PostValidator postValidator;
    private final UserValidator userValidator;
    private final PostReader postReader;

    public Long post(Long userId, CreatePost createPost) {
        postValidator.validate(createPost);
        userValidator.validateExist(userId);
        return postProcessor.post(userId, createPost);
    }

    public void removePost(Long userId, Long postId) {
        userValidator.validateExist(userId);
        postValidator.validateExist(postId);
        postValidator.isOwnedByUser(postId, userId);
        postProcessor.remove(postId);
    }

    public void updatePost(Long userId, UpdatePost updatePost) {
        postValidator.validate(updatePost);
        userValidator.validateExist(userId);
        postValidator.isOwnedByUser(updatePost.postId(), userId);
        postProcessor.update(updatePost);
    }

    public List<ThumbnailPost> readThumbnailPost(Long userId, Long targetUserId) {
        userValidator.validateExist(userId);
        userValidator.validateExist(targetUserId);
        userValidator.canViewPost(userId, targetUserId);
        return postReader.readThumbnailPost(targetUserId);
    }

    public Long readCount(Long userId) {
        userValidator.validateExist(userId);
        return postReader.readCount(userId);
    }

    public PostDetails readPostDetails(Long postId) {
        postValidator.validateExist(postId);
        return postReader.readPostDetails(postId);
    }
}
