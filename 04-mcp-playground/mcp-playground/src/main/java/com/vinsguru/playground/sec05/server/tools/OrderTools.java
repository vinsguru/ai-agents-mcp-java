package com.vinsguru.playground.sec05.server.tools;

import com.vinsguru.playground.sec05.dto.UserCategory;
import com.vinsguru.playground.sec05.dto.UserContext;
import com.vinsguru.playground.sec05.server.dto.Order;
import com.vinsguru.playground.sec05.server.dto.ToolResult;
import com.vinsguru.playground.sec05.server.exception.OrderNotFoundException;
import com.vinsguru.playground.sec05.server.service.OrderService;
import org.springframework.ai.mcp.annotation.McpMeta;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Component
public class OrderTools {

    private final OrderService orderService;
    private final JsonMapper mapper;

    public OrderTools(OrderService orderService, JsonMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @McpTool(description = "List all orders")
    public List<Order> listOrders(McpMeta meta) {
        var userContext = this.getUserContext(meta);
        return this.orderService.listOrders(userContext.userId());
    }

    @McpTool(description = "Cancel an order")
    public ToolResult cancelOrder(McpMeta meta, int orderId) {
        try{
            var userContext = this.getUserContext(meta);
            if(UserCategory.PREMIUM.equals(userContext.userCategory())){
                this.orderService.cancelOrder(userContext.userId(), orderId);
                return new ToolResult("Order cancelled successfully");
            }
            return new ToolResult("Order cancellation is allowed only for premium users");
        } catch (OrderNotFoundException e) {
            return new ToolResult(e.getMessage());
        }
    }

    private UserContext getUserContext(McpMeta meta){
        // meta.get could be null — always check before use
        return mapper.convertValue(meta.get("userContext"), UserContext.class);
    }

}
