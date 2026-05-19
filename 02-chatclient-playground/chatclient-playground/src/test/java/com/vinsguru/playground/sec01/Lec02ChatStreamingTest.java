package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec02ChatStreamingTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ChatStreamingTest.class);
    private static final String PROMPT =  "Write a long poem about Java programming";

    @Test
    void syncCall() {
        var response = this.chatClient.prompt(PROMPT)
                                      .call()
                                      .content();
        log.info(response);
    }

    @Test
    void streamingCall() {
        this.chatClient.prompt(PROMPT)
                .stream()
                .content()
                .doOnNext(System.out::println)
                .blockLast(); // we will not block like this when we use webflux (under src/main/java)
    }

}
