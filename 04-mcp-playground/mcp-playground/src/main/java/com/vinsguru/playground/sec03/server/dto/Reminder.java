package com.vinsguru.playground.sec03.server.dto;

import org.springframework.ai.mcp.annotation.McpToolParam;

import java.time.LocalDateTime;

public record Reminder(@McpToolParam(description = "Task description (e.g., Doctor appointment)") String task,
                       @McpToolParam(description = "Reminder date-time in ISO format (e.g., 2026-01-25T17:00)") LocalDateTime remindAt) {
}
