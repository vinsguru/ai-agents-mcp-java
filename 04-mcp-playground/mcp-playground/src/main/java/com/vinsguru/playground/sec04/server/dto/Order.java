package com.vinsguru.playground.sec04.server.dto;

import java.time.LocalDate;

public record Order(Integer orderId,
                    String description,
                    Integer price,
                    LocalDate orderDate) {
}
