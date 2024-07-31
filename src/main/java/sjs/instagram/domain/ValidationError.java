package sjs.instagram.domain;


public record ValidationError(
        String field,
        String message
) {
    public boolean hasField() {
        return !field.isBlank();
    }
}
