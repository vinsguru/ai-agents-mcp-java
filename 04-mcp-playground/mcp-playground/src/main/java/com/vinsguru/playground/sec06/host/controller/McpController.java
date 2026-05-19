package com.vinsguru.playground.sec06.host.controller;

import com.vinsguru.playground.sec06.host.dto.ChatRequest;
import com.vinsguru.playground.sec06.host.dto.McpSessionManifest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
public class McpController {

    private final ChatClient client;
    private final McpSessionManifest mcpSessionManifest;

    public McpController(ChatClient client, McpSessionManifest mcpSessionManifest) {
        this.client = client;
        this.mcpSessionManifest = mcpSessionManifest;
    }

    @GetMapping("manifest")
    public McpSessionManifest mcpSessionManifest() {
        return this.mcpSessionManifest;
    }

    @PostMapping
    public Flux<String> chat(@RequestBody ChatRequest request, @RequestHeader("userId") Integer userId) {
        return this.client.prompt(request.message())
                          .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, userId))
                          .system(spec -> spec.param("date", LocalDate.now()))
                          .toolContext(Map.of("userId", userId))
                          .stream()
                          .content();
    }

}
