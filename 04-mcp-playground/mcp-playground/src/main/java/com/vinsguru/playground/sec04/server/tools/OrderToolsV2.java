package com.vinsguru.playground.sec04.server.tools;

import com.vinsguru.playground.sec04.server.dto.Order;
import com.vinsguru.playground.sec04.server.dto.ToolResult;
import com.vinsguru.playground.sec04.server.service.OrderService;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "version", havingValue = "v2")
public class OrderToolsV2 {

    private final OrderService orderService;

    public OrderToolsV2(OrderService orderService) {
        this.orderService = orderService;
    }

    @McpTool(description = "List all orders")
    public List<Order> listOrders() {
        return this.orderService.listOrders();
    }

    @McpTool(description = "Cancel an order")
    public ToolResult cancelOrder(int orderId) {
        if(this.orderService.isOrderCancellable(orderId)){
            this.orderService.cancelOrder(orderId);
            return new ToolResult("Order cancelled successfully");
        }
        return new ToolResult("This order is not eligible for cancellation");
    }

}
