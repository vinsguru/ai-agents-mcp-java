package com.vinsguru.playground.sec07.servers.flight.dto;

import java.util.UUID;

public record BookingResponse(UUID bookingId,
                              String flightNumber,
                              String status) {
}
