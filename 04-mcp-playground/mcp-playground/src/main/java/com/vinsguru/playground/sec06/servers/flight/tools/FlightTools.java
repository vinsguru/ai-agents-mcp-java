package com.vinsguru.playground.sec06.servers.flight.tools;

import com.vinsguru.playground.sec06.servers.flight.dto.BookingRequest;
import com.vinsguru.playground.sec06.servers.flight.dto.BookingResponse;
import com.vinsguru.playground.sec06.servers.flight.dto.SearchRequest;
import com.vinsguru.playground.sec06.servers.flight.dto.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FlightTools {

    private static final Logger log = LoggerFactory.getLogger(FlightTools.class);

    @McpTool(description = "Search flights for a given origin, destination, and date")
    public List<SearchResponse> searchFlights(SearchRequest request) {
        log.info("searching flights. request: {}", request);

        // 1. Cheapest Flight
        var cheapFlight = new SearchResponse(
                "AA" + ThreadLocalRandom.current().nextInt(100, 1000), "American Airlines",
                request.originAirportCode(), LocalDateTime.of(request.travelDate(), LocalTime.of(8, 0)),
                request.destinationAirportCode(), LocalDateTime.of(request.travelDate(), LocalTime.of(14, 0)),
                360, 200 // Low price, long duration
        );

        // 2. Shortest Duration
        var shortFlight = new SearchResponse(
                "DL" + ThreadLocalRandom.current().nextInt(100, 1000), "Delta Airlines",
                request.originAirportCode(), LocalDateTime.of(request.travelDate(), LocalTime.of(10, 0)),
                request.destinationAirportCode(), LocalDateTime.of(request.travelDate(), LocalTime.of(13, 0)),
                180, 500 // High price, short duration
        );

        return List.of(cheapFlight, shortFlight);

    }

    @McpTool(description = "Book flight for a passenger.")
    public BookingResponse bookFlight(BookingRequest request) {
        log.info("book flight request: {}",  request);
        return new BookingResponse(UUID.randomUUID(), request.flightNumber(), "CONFIRMED");
    }

}
