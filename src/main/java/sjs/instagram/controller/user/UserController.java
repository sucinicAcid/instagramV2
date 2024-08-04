package sjs.instagram.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sjs.instagram.config.LoginUserDetails;
import sjs.instagram.domain.exception.CannotViewPostException;
import sjs.instagram.domain.exception.NoUserFoundException;
import sjs.instagram.domain.post.ThumbnailPost;
import sjs.instagram.domain.user.UserInfo;
import sjs.instagram.service.post.PostService;
import sjs.instagram.service.user.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/users/{instagramId}")
    public String userInfo(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                           @PathVariable("instagramId") String instagramId,
                           Model model) {
        try {
            Long userId = loginUserDetails.getUser().id();
            Long targetId = userService.read(instagramId);
            UserInfo userInfo = userService.readUserInfo(targetId);
            model.addAttribute("userInfo", userInfo);
            List<ThumbnailPost> posts = postService.readThumbnailPost(userId, targetId);
            model.addAttribute("posts", posts);
            return "publicUserInfo";
        } catch (NoUserFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        } catch (CannotViewPostException e) {
            Long targetId = e.getPostId();
            Long postCount = postService.readCount(targetId);
            model.addAttribute("postCount", postCount);
            return "privateUserInfo";
        }
    }
}
