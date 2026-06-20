package com.vinsguru.playground.sec07.host.config;

import com.vinsguru.playground.sec07.host.dto.McpSessionManifest;
import com.vinsguru.playground.sec07.host.dto.NotificationChannel;
import com.vinsguru.playground.sec07.host.dto.UserNotification;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider, @Value("classpath:${section}/system-message.txt") Resource systemMessage) {
        return builder.defaultSystem(systemMessage)
                      .defaultTools(toolCallbackProvider) // spring AI 2.0 related changes
                      .build();
    }

    @Bean
    public McpSessionManifest mcpSessionManifest(ChatModel chatModel, ToolCallbackProvider toolCallbackProvider, @Value("classpath:${section}/suggested-inputs.txt") Resource suggestedUserInputs) throws IOException {
        var modelName = chatModel.getOptions().getModel();
        var tools = Arrays.stream(toolCallbackProvider.getToolCallbacks())
                          .map(toolCallback -> toolCallback.getToolDefinition().name())
                          .toList();
        var suggestedInputs = Files.readAllLines(suggestedUserInputs.getFilePath());
        return new McpSessionManifest(
                modelName, tools, suggestedInputs
        );
    }

    @Bean
    public NotificationChannel notificationChannel(){
        var sink = Sinks.many().multicast().<UserNotification>onBackpressureBuffer();
        var flux = sink.asFlux();
        return new NotificationChannel(sink, flux);
    }

}
