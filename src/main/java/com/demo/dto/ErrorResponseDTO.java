package com.demo.dto;

import com.demo.exceptions.ErrorCodeEnums;

public record ErrorResponseDTO (ErrorCodeEnums error, String description) {
}
