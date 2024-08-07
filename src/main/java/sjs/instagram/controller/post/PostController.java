package sjs.instagram.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import sjs.instagram.domain.post.PostDetails;
import sjs.instagram.service.post.PostService;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{postId}")
    @ResponseBody
    public PostDetails posts(@PathVariable("postId") Long postId) {
        return postService.readPostDetails(postId);
    }
}
