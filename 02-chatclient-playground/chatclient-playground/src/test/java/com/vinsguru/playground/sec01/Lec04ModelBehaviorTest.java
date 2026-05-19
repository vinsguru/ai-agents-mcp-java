package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.stream.IntStream;

public class Lec04ModelBehaviorTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec04ModelBehaviorTest.class);

    @ParameterizedTest
    @ValueSource(ints = {5, 10})
    public void maxTokens(int tokens) {
        var prompt = "3 + 4 = ?";
        this.executePrompt(prompt, ChatOptions.builder()
                                              .maxTokens(tokens));
        /*
        * OpenAI
        * Use this: OpenAiChatOptions.builder().maxCompletionTokens(tokens)
        * */
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.5})
    public void temperature(double temperature) {
        var prompt = "Suggest a name for my new coffee shop. Return only one word.";
        var chatOptionsBuilder = ChatOptions.builder()
                                            .temperature(temperature);
        IntStream.rangeClosed(1, 3)
                 .forEach(i -> this.executePrompt(prompt, chatOptionsBuilder));
    }

    private void executePrompt(String prompt, ChatOptions.Builder<?> builder) {
        var response = this.chatClient.prompt(prompt)
                                      .options(builder) // Spring AI 2.0 change: it accepts builder
                                      .call()
                                      .content();
        log.info("{}", response);
    }
}
