package sjs.instagram.domain.comment;

public record CommentDetails(
        String instagramId,
        String userImage,
        String content,
        Long likes
) {
    public CommentDetails(String instagramId, String userImage, String content, Long likes) {
        this.instagramId = instagramId;
        this.userImage = userImage;
        this.content = content;
        this.likes = likes;
    }
}