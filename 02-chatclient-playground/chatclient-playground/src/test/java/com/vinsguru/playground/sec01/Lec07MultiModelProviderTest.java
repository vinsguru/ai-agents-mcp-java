package com.vinsguru.playground.sec01;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(properties = {
        "spring.ai.ollama.chat.options.model=gemma3:270m",
        "spring.ai.ollama.base-url=http://localhost:11434",
        "spring.ai.openai.api-key=${OPENAI_API_KEY}",
        "spring.ai.google.genai.api-key=${GEMINI_API_KEY}"
})
public class Lec07MultiModelProviderTest {

    private static final Logger log = LoggerFactory.getLogger(Lec07MultiModelProviderTest.class);

    @Autowired
    private ChatClient geminiClient;

    @Autowired
    private ChatClient openaiClient;

    @Autowired
    private ChatClient ollamaClient;

    @Test
    public void modelProvider() {
        var prompt = "If 3 pencils cost $1.20, how much do 7 pencils cost? Return only the number";
        this.executePrompt(geminiClient, "gemini", prompt);
        this.executePrompt(openaiClient, "openai", prompt);
        this.executePrompt(ollamaClient, "ollama", prompt);
    }

    private void executePrompt(ChatClient chatClient, String clientName, String prompt) {
        var response = chatClient.prompt(prompt)
                                 .call()
                                 .content();
        log.info("{} => {}", clientName, response);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ChatClient geminiClient(GoogleGenAiChatModel chatModel) {
            return ChatClient.create(chatModel);
        }

        @Bean
        public ChatClient openaiClient(OpenAiChatModel chatModel) {
            return ChatClient.create(chatModel);
        }

        @Bean
        public ChatClient ollamaClient(OllamaChatModel chatModel) {
            return ChatClient.create(chatModel);
        }

    }

}
