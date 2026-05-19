package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

public class Lec06MultiModelBeansTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec06MultiModelBeansTest.class);

    @Autowired
    private ChatClient proModel;

    @Autowired
    private ChatClient liteModel;

    @Test
    public void modelVariants() {
        var prompt = "If 3 pencils cost $1.20, how much do 7 pencils cost? Return only the number";

        log.info("---- PRO MODEL ----");
        this.executePrompt(proModel, prompt);

        log.info("---- LITE MODEL ----");
        this.executePrompt(liteModel, prompt);
    }

    private void executePrompt(ChatClient chatClient, String prompt) {
        var response = chatClient.prompt(prompt)
                                 .call()
                                 .content();
        log.info("{}", response);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ChatClient proModel(ChatClient.Builder builder) {
            return this.createClient(builder, "gemini-2.5-pro");
        }

        @Bean
        public ChatClient liteModel(ChatClient.Builder builder) {
            return this.createClient(builder, "gemini-2.5-flash-lite");
        }

        private ChatClient createClient(ChatClient.Builder builder, String modelName) {
            var chatOptions = ChatOptions.builder()
                                         .model(modelName);
            return builder.defaultOptions(chatOptions)// Spring AI 2.0 change: it accepts builder
                          .build();
        }

    }

}
