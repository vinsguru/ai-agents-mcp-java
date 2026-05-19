package com.vinsguru.playground;

import com.vinsguru.playground.sec05.SectionRunner;
import com.vinsguru.playground.sec05.dto.UserCategory;
import com.vinsguru.playground.sec05.dto.UserContext;
import com.vinsguru.playground.sec05.server.dto.Order;
import com.vinsguru.playground.sec05.server.dto.ToolResult;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootTest(
        classes = SectionRunner.Server.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "section=sec05",
                "config=server"
        }
)
class Lec01CallToolTest extends AbstractMcpTest {

    @BeforeAll
    public void initialize() {
        mcpClient = McpClient.sync(createTransport())
                                  .build();
    }

    @Test
    void toolsCount() {
        Assertions.assertEquals(2, mcpClient.listTools()
                                            .tools()
                                            .size());
    }

    @Test
    void listOrders() {
        var request = McpSchema.CallToolRequest.builder()
                                               .name("listOrders")
                                               .meta(Map.of("userContext", new UserContext(1, UserCategory.STANDARD)))
                                               .build();
        var result = mcpClient.callTool(request);
        var orders = readToolResponse(result, new TypeReference<List<Order>>() {
        });
        Assertions.assertEquals(3, orders.size());
    }

    @ParameterizedTest
    @MethodSource("cancelOrderScenarios")
    void cancelOrder(int orderId, UserContext userContext, String expectedMessage) {
        var request = McpSchema.CallToolRequest.builder()
                                               .name("cancelOrder")
                                               .meta(Map.of("userContext", userContext))
                                               .arguments(Map.of("orderId", orderId))
                                               .build();
        var result = mcpClient.callTool(request);
        var toolResult = readToolResponse(result, new TypeReference<ToolResult>() {
        });
        Assertions.assertEquals(expectedMessage, toolResult.message());
    }

    public Stream<Arguments> cancelOrderScenarios() {
        return Stream.of(
                Arguments.of(1, new UserContext(1, UserCategory.PREMIUM), "Order cancelled successfully"),
                Arguments.of(10, new UserContext(1, UserCategory.PREMIUM), "Order id 10 is not found"),
                Arguments.of(1, new UserContext(1, UserCategory.STANDARD), "Order cancellation is allowed only for premium users")
        );
    }

}
