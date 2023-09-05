package com.demo.dto;

import com.demo.exceptions.ErrorCode;

public record ErrorResponseDTO (ErrorCode error, String description) {
}
