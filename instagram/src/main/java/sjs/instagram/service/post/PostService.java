package sjs.instagram.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.post.CreatePost;
import sjs.instagram.domain.post.PostProcessor;
import sjs.instagram.domain.post.PostValidator;
import sjs.instagram.domain.user.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostProcessor postProcessor;
    private final PostValidator postValidator;
    private final UserValidator userValidator;

    public Long post(Long userId, CreatePost createPost) {
        postValidator.validate(createPost);
        userValidator.validateExist(userId);
        return postProcessor.post(userId, createPost);
    }
}
