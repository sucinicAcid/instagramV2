package sjs.instagram.domain.post;

public record UpdatePost(
        Long postId,
        String title,
        String content
) {
}
