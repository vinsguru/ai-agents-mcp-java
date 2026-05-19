package com.vinsguru.playground.sec07.servers.flight.dto;

import java.time.LocalDateTime;

public record SearchResponse(String flightNumber,
                             String airline,
                             String originAirportCode,
                             LocalDateTime departureTime,
                             String destinationAirportCode,
                             LocalDateTime arrivalTime,
                             Integer flightDurationInMinutes,
                             Integer price) {
}
