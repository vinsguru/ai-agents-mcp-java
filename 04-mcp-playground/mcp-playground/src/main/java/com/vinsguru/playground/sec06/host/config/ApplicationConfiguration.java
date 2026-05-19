package com.vinsguru.playground.sec06.host.config;

import com.vinsguru.playground.sec06.host.dto.McpSessionManifest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider, @Value("classpath:${section}/system-message.txt") Resource systemMessage) {
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(20).build();
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder.defaultSystem(systemMessage)
                      .defaultToolCallbacks(toolCallbackProvider)
                      .defaultAdvisors(chatMemoryAdvisor)
                      .build();
    }

    @Bean
    public McpSessionManifest mcpSessionManifest(ChatModel chatModel, ToolCallbackProvider toolCallbackProvider, @Value("classpath:${section}/suggested-inputs.txt") Resource suggestedUserInputs) throws IOException {
        var modelName = chatModel.getDefaultOptions().getModel();
        var tools = Arrays.stream(toolCallbackProvider.getToolCallbacks())
                          .map(toolCallback -> toolCallback.getToolDefinition().name())
                          .toList();
        var suggestedInputs = Files.readAllLines(suggestedUserInputs.getFilePath());
        return new McpSessionManifest(
                modelName, tools, suggestedInputs
        );
    }

}
