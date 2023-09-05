package com.demo.exceptions;

public class CustomizedException extends RuntimeException {
    private final String description;
    private final ErrorCode code;

    public CustomizedException(String description, ErrorCode code) {
        super(description);
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ErrorCode getCode() {
        return code;
    }
}

