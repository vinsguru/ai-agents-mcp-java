package com.vinsguru.playground.sec05.server.service;

import com.vinsguru.playground.sec05.server.dto.Order;
import com.vinsguru.playground.sec05.server.exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final Map<Integer, List<Order>> userOrders = Map.of(
            1, List.of(
                    new Order(1, "iphone", 990, LocalDate.now().minusDays(10)),
                    new Order(2, "ipad", 500, LocalDate.now().minusDays(5)),
                    new Order(3, "macbook", 2500, LocalDate.now().minusDays(1))
            ),
            2, List.of(
                    new Order(35, "pixel", 900, LocalDate.now().minusDays(10)),
                    new Order(36, "sony tv", 500, LocalDate.now().minusDays(2))
            )
    );

    public List<Order> listOrders(int userId) {
        log.info("providing orders for user: {}", userId);
        return userOrders.getOrDefault(userId, Collections.emptyList());
    }

    public void cancelOrder(int userId, int orderId) {
        log.info("cancelling order. userId: {}, orderId: {}", userId, orderId);
        var order = this.findByUserIdAndOrderId(userId, orderId);
        // skip
    }

    // simulating order repository
    private Order findByUserIdAndOrderId(int userId, int orderId) {
        return this.userOrders.get(userId)
                              .stream()
                              .filter(order -> order.orderId().equals(orderId))
                              .findFirst()
                              .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
