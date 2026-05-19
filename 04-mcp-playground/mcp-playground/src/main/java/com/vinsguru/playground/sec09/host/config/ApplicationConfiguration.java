package com.vinsguru.playground.sec09.host.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, @Value("classpath:${section}/system-message.txt") Resource systemMessage, @Value("classpath:${section}/user-message.txt") Resource userMessage) {
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(5).build();
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder.defaultAdvisors(chatMemoryAdvisor)
                      .defaultSystem(systemMessage)
                      .defaultUser(userMessage)
                      .build();
    }

    @Bean
    public Map<String, McpSyncClient> mcpClients(List<McpSyncClient> syncClients) {
        log.info("sync clients size: {}", syncClients.size());
        return syncClients.stream()
                          .collect(Collectors.toMap(
                                     c -> c.getClientInfo().title(),
                                     Function.identity()
                             ));
    }

}
