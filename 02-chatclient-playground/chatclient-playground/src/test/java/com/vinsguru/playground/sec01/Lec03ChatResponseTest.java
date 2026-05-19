package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ChatResponse represents the full response returned by the AI model.
// It contains the generated output, metadata, and other provider details.
// Rate limit information depends on the AI provider.
// Some providers (Ollama or Gemini) do not return this data.
public class Lec03ChatResponseTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03ChatResponseTest.class);

    @Test
    public void chatResponse() {
        var response = this.chatClient.prompt("3 + 2 = ?")
                                      .call()
                                      .chatResponse();
        log.info("{}", response);
        // usage: response.getMetadata().getUsage()
        // text: response.getResult().getOutput().getText()
    }

}
