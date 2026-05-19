package com.vinsguru.playground.sec03.host.controller;

import com.vinsguru.playground.sec03.host.dto.ChatRequest;
import com.vinsguru.playground.sec03.host.dto.McpSessionManifest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

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
    public McpSessionManifest mcpSessionManifest(){
        return this.mcpSessionManifest;
    }

    @PostMapping
    public Flux<String> chat(@RequestBody ChatRequest request) {
        return this.client.prompt(request.message())
                          .system(spec -> spec.param("dateTime", LocalDateTime.now()))
                          .stream()
                          .content();
    }

}
