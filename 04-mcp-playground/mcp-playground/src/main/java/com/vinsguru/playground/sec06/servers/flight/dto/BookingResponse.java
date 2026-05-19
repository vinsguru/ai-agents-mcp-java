package com.vinsguru.playground.sec06.servers.flight.dto;

import java.util.UUID;

public record BookingResponse(UUID bookingId,
                              String flightNumber,
                              String status) {
}
