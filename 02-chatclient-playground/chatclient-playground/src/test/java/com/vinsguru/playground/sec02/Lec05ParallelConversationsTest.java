package com.vinsguru.playground.sec02;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

import java.util.concurrent.Executors;

public class Lec05ParallelConversationsTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05ParallelConversationsTest.class);

    @BeforeAll
    public void setup() {
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(20).build();
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = builder.defaultSystem("You are a helpful assistant. Answer briefly.")
                                 .defaultAdvisors(chatMemoryAdvisor)
                                 .build();
    }

    @Test
    public void parallelConversations() {

        Runnable samConversation = () -> {
            this.chat("sam", "My number is 10"); // 1
            sleep(9000);
            this.chat("sam", "Add 5 to it. What is the result?"); // 4
        };

        Runnable mikeConversation = () -> {
            sleep(3000);
            this.chat("mike", "My number is 20"); // 2
            sleep(3000);
            this.chat("mike", "Multiply it by 2. What is the result?"); // 3
        };

        // execute these 2 conversations concurrently
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(samConversation);
            executor.submit(mikeConversation);
        }

    }

    private void chat(String conversationId, String userMessage) {
        var assistantMessage = this.chatClient.prompt(userMessage)
                                              .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                                              .call()
                                              .content();
        log.info("[{}] Q: {}", conversationId, userMessage);
        log.info("[{}] A: {}", conversationId, assistantMessage);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
