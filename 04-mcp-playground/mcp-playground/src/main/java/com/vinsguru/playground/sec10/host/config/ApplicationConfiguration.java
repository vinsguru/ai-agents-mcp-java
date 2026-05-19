package com.vinsguru.playground.sec10.host.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
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
