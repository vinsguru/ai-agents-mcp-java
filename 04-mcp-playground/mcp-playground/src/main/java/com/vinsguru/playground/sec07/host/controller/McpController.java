package com.vinsguru.playground.sec07.host.controller;

import com.vinsguru.playground.sec07.host.dto.ChatRequest;
import com.vinsguru.playground.sec07.host.dto.McpSessionManifest;
import com.vinsguru.playground.sec07.host.dto.NotificationChannel;
import com.vinsguru.playground.sec07.host.dto.UserNotification;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
public class McpController {

    private final ChatClient client;
    private final McpSessionManifest mcpSessionManifest;
    private final NotificationChannel notificationChannel;

    public McpController(ChatClient client, McpSessionManifest mcpSessionManifest, NotificationChannel notificationChannel) {
        this.client = client;
        this.mcpSessionManifest = mcpSessionManifest;
        this.notificationChannel = notificationChannel;
    }

    @GetMapping("manifest")
    public McpSessionManifest mcpSessionManifest() {
        return this.mcpSessionManifest;
    }

    @PostMapping
    public Flux<String> chat(@RequestBody ChatRequest request, @RequestHeader("userId") Integer userId) {
        var context = Map.<String, Object>of(
                "userId", userId,
                "progressToken", request.progressToken()
        );
        return this.client.prompt(request.message())
                          .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, userId))
                          .system(spec -> spec.param("date", LocalDate.now()))
                          .toolContext(context)
                          .stream()
                          .content();
    }

    @GetMapping(value = "notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserNotification> notification(@RequestParam String progressToken) {
        return this.notificationChannel.stream(progressToken);
    }

}
