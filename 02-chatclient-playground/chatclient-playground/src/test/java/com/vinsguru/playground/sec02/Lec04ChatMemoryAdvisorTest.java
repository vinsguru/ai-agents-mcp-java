package com.vinsguru.playground.sec02;


import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

/*
 * This demo uses the default conversation ID intentionally!
 * */
public class Lec04ChatMemoryAdvisorTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec04ChatMemoryAdvisorTest.class);

    @BeforeAll
    public void setup() { // This is for demo
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(20).build();
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = builder.defaultSystem("You are a helpful assistant. Answer briefly.")
                                 // spring AI 2.0 change: we need to provide conversation id explicitly. will explain in the next lec
                                 .defaultAdvisors(spec -> spec.advisors(chatMemoryAdvisor).param(ChatMemory.CONVERSATION_ID, "default"))
                                 .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "what is the value of 3 * 4?",
            "what about 5 and 6?",
            "what if we divide that by 3?"
    })
    public void chatMemory(String userMessage) {
        var assistantMessage = this.chatClient.prompt(userMessage)
                                              .call()
                                              .content();
        log.info("Q: {}", userMessage);
        log.info("A: {}", assistantMessage);
    }

}
