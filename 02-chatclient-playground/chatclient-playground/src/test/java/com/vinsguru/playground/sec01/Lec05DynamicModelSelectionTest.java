package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.ChatOptions;

/*
Model Names:
OpenAI: https://developers.openai.com/api/docs/models
Google: https://ai.google.dev/gemini-api/docs/models
Ollama: https://ollama.com/library/gemma3
* */

public class Lec05DynamicModelSelectionTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05DynamicModelSelectionTest.class);

    @ParameterizedTest
    @ValueSource(strings = {"gemma3:270m", "gemma3:1b"})
    void dynamicModelSelection(String model) {
        var prompt = "If 3 pencils cost $1.20, how much do 7 pencils cost? Return only the number";
        this.executePrompt(prompt, model);
    }

    private void executePrompt(String prompt, String model) {
        var options = ChatOptions.builder()
                                 .model(model);
        var response = this.chatClient.prompt(prompt)
                                 .options(options) // Spring AI 2.0 change: it accepts builder
                                 .call()
                                 .content();
        log.info("{}", response);
    }

}
