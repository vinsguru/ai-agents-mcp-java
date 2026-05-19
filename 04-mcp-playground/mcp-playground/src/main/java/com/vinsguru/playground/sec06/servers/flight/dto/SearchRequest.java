package com.vinsguru.playground.sec06.servers.flight.dto;

import org.springframework.ai.mcp.annotation.McpToolParam;

import java.time.LocalDate;

public record SearchRequest(
        @McpToolParam(description = "Origin Airport Code") String originAirportCode,
        @McpToolParam(description = "Destination Airport Code") String destinationAirportCode,
        @McpToolParam(description = "Departure date in ISO format (e.g., 2026-04-26)") LocalDate travelDate) {
}
