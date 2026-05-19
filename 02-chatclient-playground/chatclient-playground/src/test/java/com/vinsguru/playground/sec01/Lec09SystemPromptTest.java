package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec09SystemPromptTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec09SystemPromptTest.class);

    @ParameterizedTest
    @ValueSource(strings = {
            "You are a physics teacher. Answer briefly.",
            "You are a sound engineer. Answer briefly.",
            "You are a finance expert. Answer briefly."
    })
    public void domainBasedResponses(String systemInstruction) {
        this.executePrompt(systemInstruction, "What is volume?");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "You are a sarcastic math teacher.",
            "You are a helpful math teacher."
    })
    public void toneBasedResponses(String systemInstruction) {
        this.executePrompt(systemInstruction, "What is square root of 100?");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "You are a helpful assistant. Answer in one word.",
            "You are a helpful assistant. Answer in one sentence."
    })
    public void responseConstraints(String systemInstruction){
        this.executePrompt(systemInstruction, "Explain Java");
    }

    private void executePrompt(String systemInstruction, String userPrompt) {
        var response = chatClient.prompt()
                                 .system(systemInstruction)
                                 .user(userPrompt)
                                 .call()
                                 .content();
        log.info("{}", response);
    }

}
