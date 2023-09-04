package com.demo.exceptions;

public class OutOfIdException extends RuntimeException {
    private final String description;
    private final ErrorCode code;

    public OutOfIdException(String description, ErrorCode code) {
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

