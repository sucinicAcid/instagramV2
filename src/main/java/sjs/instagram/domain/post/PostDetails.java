package sjs.instagram.domain.post;

import sjs.instagram.domain.comment.CommentDetails;

import java.util.ArrayList;
import java.util.List;

public record PostDetails(
        String name,
        String userImage,
        List<String> postImages,
        String title,
        String content,
        Long likes,
        List<CommentDetails> comments
) {
    public PostDetails(String name, String userImage, String title, String content, Long likes) {
        this(name, userImage, new ArrayList<>(), title, content, likes, new ArrayList<>());
    }

    public PostDetails(String name, String userImage, List<String> postImages, String title, String content, Long likes, List<CommentDetails> comments) {
        this.name = name;
        this.userImage = userImage;
        this.postImages = postImages;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
    }
}
