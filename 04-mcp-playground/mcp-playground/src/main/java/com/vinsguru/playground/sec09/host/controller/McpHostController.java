package com.vinsguru.playground.sec09.host.controller;

import com.vinsguru.playground.sec09.host.dto.ChatRequest;
import com.vinsguru.playground.sec09.host.service.UserService;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    @McpClient("job-service")
    private McpSyncClient client;
* */

@RestController
@RequestMapping("/api/mcp")
public class McpHostController {

    private final ChatClient chatClient;
    private final UserService userService;
    private final McpSyncClient mcpClient;

    public McpHostController(ChatClient chatClient, UserService userService, Map<String, McpSyncClient> mcpClients) {
        this.chatClient = chatClient;
        this.userService = userService;
        this.mcpClient = mcpClients.get("job-service");
    }

    @GetMapping("resources")
    public List<McpSchema.Resource> listResources() {
        return this.mcpClient.listResources()
                             .resources();
    }

    @PostMapping("resources")
    public List<McpSchema.ResourceContents> readResourceContents(@RequestBody McpSchema.ReadResourceRequest readResourceRequest) {
        return this.mcpClient.readResource(readResourceRequest)
                             .contents();
    }

    @PostMapping("chat")
    public Flux<String> chat(@RequestBody ChatRequest request, @RequestHeader("userId") Integer userId) {
        var params = Map.<String, Object>of(
                "userQuestion", request.message(),
                "userProfile", this.userService.getUserProfile(userId),
                "jobDetails", this.readResourceContent(request.resourceUri())
        );
        return this.chatClient.prompt()
                              .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, userId)) // for demo
                              .user(spec -> spec.params(params))
                              .stream()
                              .content();
    }

    private String readResourceContent(String resourceUri) {
        return this.mcpClient.readResource(new McpSchema.ReadResourceRequest(resourceUri))
                             .contents()
                             .stream()
                             .filter(McpSchema.TextResourceContents.class::isInstance)
                             .map(resourceContents -> ((McpSchema.TextResourceContents) resourceContents).text())
                             .collect(Collectors.joining("\n"));
    }

}
