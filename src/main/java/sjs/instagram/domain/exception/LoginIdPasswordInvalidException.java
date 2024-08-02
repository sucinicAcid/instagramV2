package sjs.instagram.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class LoginIdPasswordInvalidException extends RuntimeException {
    private List<ValidationError> errors;
    public LoginIdPasswordInvalidException(List<ValidationError> errors) {
        this.errors = errors;
    }
}
