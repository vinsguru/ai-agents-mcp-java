package com.vinsguru.playground.sec10.host.util;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

public class PromptMapper {

    public static Prompt toPrompt(List<McpSchema.PromptMessage> promptMessages) {
        var messages = promptMessages.stream()
                                     .map(PromptMapper::toMessage)
                                     .toList();
        return Prompt.builder()
                     .messages(messages)
                     .build();
    }

    private static Message toMessage(McpSchema.PromptMessage promptMessage) {
        var content = extractText(promptMessage.content());
        return switch (promptMessage.role()) {
            case ASSISTANT -> AssistantMessage.builder().content(content).build();
            case USER -> UserMessage.builder().text(content).build();
        };
    }

    private static String extractText(McpSchema.Content content){
        return switch (content){
            case McpSchema.TextContent t -> t.text();
            case null, default -> throw new IllegalStateException("expected text content");
        };
    }

}
