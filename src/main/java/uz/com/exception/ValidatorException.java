package uz.com.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ValidatorException extends RuntimeException {

    @JsonIgnore
    public static String suppressed;

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException() {
    }

    @JsonIgnore
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @JsonIgnore
    @Override
    public synchronized Throwable getCause() {
        return null;
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return null;
    }
}
