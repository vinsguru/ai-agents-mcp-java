package com.vinsguru.playground.sec07.servers.weather.dto;

import org.springframework.ai.mcp.annotation.McpToolParam;

import java.time.LocalDateTime;

public record WeatherRequest(@McpToolParam(description = "Airport Code for the destination. (e.g., JFK)") String airportCode,
                             @McpToolParam(description = "Destination arrival date-time in ISO format (e.g., 2026-01-25T17:00)") LocalDateTime arrivalTime) {
}
