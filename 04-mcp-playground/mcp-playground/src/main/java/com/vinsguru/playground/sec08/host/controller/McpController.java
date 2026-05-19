package com.vinsguru.playground.sec08.host.controller;

import com.vinsguru.playground.sec08.host.dto.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
public class McpController {

    private final ChatClient client;
    private final McpSessionManifest mcpSessionManifest;
    private final NotificationChannel<UserNotification> notificationChannel;
    private final NotificationChannel<UserNotificationResponse> notificationResponseChannel;

    public McpController(ChatClient client, McpSessionManifest mcpSessionManifest, NotificationChannel<UserNotification> notificationChannel, NotificationChannel<UserNotificationResponse> notificationResponseChannel) {
        this.client = client;
        this.mcpSessionManifest = mcpSessionManifest;
        this.notificationChannel = notificationChannel;
        this.notificationResponseChannel = notificationResponseChannel;
    }

    @GetMapping("manifest")
    public McpSessionManifest mcpSessionManifest() {
        return this.mcpSessionManifest;
    }

    @PostMapping
    public Flux<String> chat(@RequestBody ChatRequest request) {
        return this.client.prompt(request.message())
                          .toolContext(Map.of("progressToken", request.progressToken()))
                          .stream()
                          .content();
    }

    @GetMapping(value = "notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserNotification> notification(@RequestParam String progressToken) {
        return this.notificationChannel.stream(progressToken);
    }

    @PostMapping("notifications")
    public void handleUserResponse(@RequestBody UserNotificationResponse userResponse) {
        this.notificationResponseChannel.emit(userResponse);
    }

}
