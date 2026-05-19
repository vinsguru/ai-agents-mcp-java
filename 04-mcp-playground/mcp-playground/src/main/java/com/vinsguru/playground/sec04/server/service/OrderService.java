package com.vinsguru.playground.sec04.server.service;

import com.vinsguru.playground.sec04.server.dto.Order;
import com.vinsguru.playground.sec04.server.exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final int CANCELLATION_WINDOW_DAYS = 7;

    private final List<Order> orders = List.of( // we can use map as well.
            new Order(1, "iphone", 990, LocalDate.now().minusDays(10)),
            new Order(2, "ipad", 500, LocalDate.now().minusDays(5)),
            new Order(3, "macbook", 2500, LocalDate.now().minusDays(1))
    );

    public List<Order> listOrders() {
        log.info("providing orders");
        return this.orders;
    }

    public boolean isOrderCancellable(int orderId) {
        log.info("checking if the order {} is eligible for cancellation", orderId);
        var order = this.findById(orderId);
        return LocalDate.now()
                        .minusDays(CANCELLATION_WINDOW_DAYS)
                        .isBefore(order.orderDate());
    }

    public void cancelOrder(int orderId) {
        log.info("cancelling order {}", orderId);
        // skip
    }

    // simulating order repository
    private Order findById(int orderId) {
        return orders.stream()
                     .filter(order -> order.orderId().equals(orderId))
                     .findFirst()
                     .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
