package com.vinsguru.playground.sec10.host.controller;

import com.vinsguru.playground.sec10.host.util.PromptMapper;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mcp/prompts")
public class McpHostController {

    private final ChatClient chatClient;
    private final McpSyncClient mcpClient;

    public McpHostController(ChatClient chatClient, Map<String, McpSyncClient> mcpClients) {
        this.chatClient = chatClient;
        this.mcpClient = mcpClients.get("codebase-service");
    }

    @GetMapping
    public List<McpSchema.Prompt> listPrompts() {
        return this.mcpClient.listPrompts()
                             .prompts();
    }

    @PostMapping("complete")
    public McpSchema.CompleteResult.CompleteCompletion complete(@RequestBody McpSchema.CompleteRequest completeRequest) {
        return this.mcpClient.completeCompletion(completeRequest)
                             .completion();
    }

    @PostMapping("invoke")
    public Flux<String> invokePrompt(@RequestBody McpSchema.GetPromptRequest request) {
        var promptResult = this.mcpClient.getPrompt(request);
        var prompt = PromptMapper.toPrompt(promptResult.messages());
        return this.chatClient.prompt(prompt)
                              .stream()
                              .content();
    }

}
