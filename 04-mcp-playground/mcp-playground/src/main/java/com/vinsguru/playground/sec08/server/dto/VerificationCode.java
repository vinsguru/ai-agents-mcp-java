package com.vinsguru.playground.sec08.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record VerificationCode(@Schema(title = "Verification Code", requiredMode = Schema.RequiredMode.REQUIRED)
                                String code) {
}
