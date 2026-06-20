package com.vinsguru.playground.sec08.host.config;


import com.vinsguru.playground.sec08.host.dto.NotificationChannel;
import com.vinsguru.playground.sec08.host.dto.UserNotification;
import com.vinsguru.playground.sec08.host.dto.UserNotificationResponse;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpElicitation;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@Component
public class ElicitRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(ElicitRequestHandler.class);
    private final NotificationChannel<UserNotification> notificationChannel;
    private final NotificationChannel<UserNotificationResponse> notificationResponseChannel;

    public ElicitRequestHandler(NotificationChannel<UserNotification> notificationChannel, NotificationChannel<UserNotificationResponse> notificationResponseChannel) {
        this.notificationChannel = notificationChannel;
        this.notificationResponseChannel = notificationResponseChannel;
    }

    @McpElicitation(clients = "file-service")
    public McpSchema.ElicitResult handleElicitRequest(McpSchema.ElicitFormRequest request) {
        log.info("elicit request: {}", request);

        // notify user
        var progressToken = request.progressToken().toString();
        var notification = new UserNotification(progressToken, request.message(), request.requestedSchema());
        this.notificationChannel.emit(notification);

        // Wait for user response
        // This call blocks the current thread. Use virtual threads for scalability.
        // spring.threads.virtual.enabled=true
        return this.notificationResponseChannel.stream(progressToken)
                                               .next()
                                               .map(response -> this.toElicitResult(response.confirmed(), response.inputData()))
                                               .timeout(Duration.ofSeconds(50))
                                               .onErrorReturn(this.toElicitResult(false, Collections.emptyMap()))
                                               .block();
    }

    private McpSchema.ElicitResult toElicitResult(boolean confirmed, Map<String, Object> inputData) {
        var action = confirmed ? McpSchema.ElicitResult.Action.ACCEPT : McpSchema.ElicitResult.Action.DECLINE;
        return McpSchema.ElicitResult.builder(action)
                                     .content(inputData)
                                     .build();
    }

}
