package com.vinsguru.playground.sec04.host.controller;

import com.vinsguru.playground.sec04.host.dto.ChatRequest;
import com.vinsguru.playground.sec04.host.dto.McpSessionManifest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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
                          .stream()
                          .content();
    }

}
