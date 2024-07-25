package sjs.instagram.domain.post;

public record ThumbnailPost(
        Long postId,
        String storeFileName
) {
    public ThumbnailPost(Long postId, String storeFileName) {
        this.postId = postId;
        this.storeFileName = storeFileName;
    }
}
