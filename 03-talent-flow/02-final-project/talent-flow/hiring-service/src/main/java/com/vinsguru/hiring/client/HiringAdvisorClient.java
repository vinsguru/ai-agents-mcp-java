package com.vinsguru.hiring.client;

import com.vinsguru.hiring.dto.JobApplicationEvaluationRequest;
import com.vinsguru.hiring.dto.JobApplicationEvaluationResponse;
import org.springframework.ai.chat.client.ChatClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

public class HiringAdvisorClient {

    private final ChatClient chatClient;
    private final JsonMapper jsonMapper;

    public HiringAdvisorClient(ChatClient chatClient, JsonMapper jsonMapper) {
        this.chatClient = chatClient;
        this.jsonMapper = jsonMapper;
    }

    public JobApplicationEvaluationResponse evaluate(JobApplicationEvaluationRequest request) {
        return this.chatClient.prompt()
                              .user(spec -> spec.params(this.toPromptParams(request)))
                              .call()
                              .entity(JobApplicationEvaluationResponse.class);
    }

    private Map<String, Object> toPromptParams(JobApplicationEvaluationRequest request) {
        return this.jsonMapper.convertValue(request, new TypeReference<Map<String, Object>>() {
        });
    }

}
