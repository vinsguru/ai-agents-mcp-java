package com.vinsguru.playground.sec05.server.exception;

public class OrderNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Order id %d is not found";

    public OrderNotFoundException(Integer id) {
        super(MESSAGE.formatted(id));
    }

}
