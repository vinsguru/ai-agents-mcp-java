package com.vinsguru.playground.sec07.servers.flight.dto;

public record Passenger(String name,
                        Address address) {

    public record Address(String street,
                   String city,
                   String state) {
    }

}
