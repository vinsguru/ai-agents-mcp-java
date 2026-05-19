package com.vinsguru.agent.controller;

import com.vinsguru.agent.dto.AnalyticsRequest;
import com.vinsguru.agent.dto.AnalyticsResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsAgentController {

    private final ChatClient chatClient;

    public AnalyticsAgentController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public AnalyticsResponse analyze(@RequestBody AnalyticsRequest request) {
        return this.chatClient.prompt(request.message())
                              .call()
                              .entity(AnalyticsResponse.class);
    }

}
