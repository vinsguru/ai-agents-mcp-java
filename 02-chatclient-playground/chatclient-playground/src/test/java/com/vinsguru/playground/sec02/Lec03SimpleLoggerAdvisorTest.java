package com.vinsguru.playground.sec02;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "logging.level.org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor=DEBUG"
})
public class Lec03SimpleLoggerAdvisorTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03SimpleLoggerAdvisorTest.class);

    @Test
    public void loggingAdvisor() {
        var response = this.chatClient.prompt("3 + 2 = ?")
                                      .advisors(new SimpleLoggerAdvisor())
                                      .call()
                                      .content();
        log.info(response);
    }

}
