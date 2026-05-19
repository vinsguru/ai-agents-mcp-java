package com.vinsguru.agent.config;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AnalyticsAgentConfig {

    @Bean // I would remove this when @McpClient is available
    public Map<String, McpSyncClient> mcpClients(List<McpSyncClient> syncClients) {
        return syncClients.stream()
                          .collect(Collectors.toMap(
                                  c -> c.getClientInfo().title(),
                                  Function.identity()
                          ));
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider, ResourceLoader resourceLoader, Map<String, McpSyncClient> mcpClients) {
        var systemMessage = resourceLoader.getResource("classpath:system-message.txt");
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(5).build();
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        var schema = mcpClients.get("analytics-agent-server")
                               .readResource(new McpSchema.ReadResourceRequest("analytics://schema/orders"))
                               .contents()
                               .stream()
                               .filter(McpSchema.TextResourceContents.class::isInstance)
                               .map(resourceContents -> ((McpSchema.TextResourceContents) resourceContents).text())
                               .collect(Collectors.joining("\n"));
        return builder.defaultSystem(spec -> spec.text(systemMessage).param("schema", schema))
                      .defaultToolCallbacks(toolCallbackProvider)
                      .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT.andThen(spec ->
                              spec.advisors(chatMemoryAdvisor).param(ChatMemory.CONVERSATION_ID, "default")) // intentional
                      ).build();
    }

}
