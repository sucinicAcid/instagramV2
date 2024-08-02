package sjs.instagram.domain.exception;


public record ValidationError(
        String field,
        String message
) {
    public boolean hasField() {
        return !field.isBlank();
    }
}
