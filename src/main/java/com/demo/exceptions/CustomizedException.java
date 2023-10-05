package com.demo.exceptions;

import lombok.Getter;

@Getter
public class CustomizedException extends RuntimeException {
    private final String description;
    private final ErrorCode code;

    public CustomizedException(String description, ErrorCode code) {
        super(description);
        this.description = description;
        this.code = code;
    }

}

