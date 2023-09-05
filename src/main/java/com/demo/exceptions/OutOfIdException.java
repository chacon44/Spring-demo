package com.demo.exceptions;

public class OutOfIdException extends RuntimeException {
    private final String description;
    private final ErrorCodeEnums code;

    public OutOfIdException(String description, ErrorCodeEnums code) {
        super(description);
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ErrorCodeEnums getCode() {
        return code;
    }
}

