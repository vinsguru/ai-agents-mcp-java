package com.vinsguru.playground.sec06.servers.flight.dto;

import org.springframework.ai.mcp.annotation.McpToolParam;

import java.time.LocalDate;

public record BookingRequest(@McpToolParam(description = "Flight number (e.g., UA202)") String flightNumber,
                             @McpToolParam(description = "Departure date in ISO format (e.g., 2026-04-26)") LocalDate travelDate,
                             @McpToolParam(description = "Passenger name and address details") Passenger passenger){
}
