package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01BasicChatTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01BasicChatTest.class);

    @Test
    void basicChat() {
        var response = this.chatClient.prompt("What is the capital of USA?")
                                      .call()
                                      .content();
        log.info(response);
    }

}
