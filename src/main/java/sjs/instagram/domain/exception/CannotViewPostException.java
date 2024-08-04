package sjs.instagram.domain.exception;

import lombok.Getter;

@Getter
public class CannotViewPostException extends RuntimeException {
    private Long postId;
    public CannotViewPostException(Long postId, String message) {
        super(message);
        this.postId = postId;
    }
}
