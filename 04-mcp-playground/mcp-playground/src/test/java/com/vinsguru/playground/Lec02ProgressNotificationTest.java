package com.vinsguru.playground;

import com.vinsguru.playground.sec07.SectionRunner;
import com.vinsguru.playground.sec07.servers.weather.dto.WeatherRequest;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootTest(
        classes = SectionRunner.WeatherService.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "section=sec07",
                "config=weather-service"
        }
)
class Lec02ProgressNotificationTest extends AbstractMcpTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ProgressNotificationTest.class);
    private BlockingQueue<McpSchema.ProgressNotification> notificationQueue;

    @BeforeAll
    public void initialize() {
        notificationQueue = new LinkedBlockingQueue<>();
        mcpClient = McpClient.sync(createTransport())
                             .progressConsumer(notificationQueue::add)
                             .build();
    }

    @Test
    void weatherProgress() {
        var request = McpSchema.CallToolRequest.builder()
                                               .name("getWeatherForecast")
                                               .arguments(Map.of("request", new WeatherRequest("ATL", LocalDateTime.now())))
                                               .progressToken("123")
                                               .build();
        mcpClient.callTool(request); // ignoring the response
        var notification = notificationQueue.element();
        log.info("notification: {}", notification);
        Assertions.assertNotNull(notification.message());
    }

}
