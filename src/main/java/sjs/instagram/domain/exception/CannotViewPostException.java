package sjs.instagram.domain.exception;

public class CannotViewPostException extends RuntimeException {
    public CannotViewPostException(String message) {
        super(message);
    }
}
