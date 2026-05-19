package com.vinsguru.playground.sec02;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

public class Lec01CustomAdvisorTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01CustomAdvisorTest.class);

    @Test
    public void loggingAdvisor() {
        var response = this.chatClient.prompt("3 + 2 = ?")
                                      .advisors(new LoggingAdvisor())
                                      .call()
                                      .content();
        log.info("Final response: {}", response);
    }

    // This advisor acts like a middleware or interceptor (similar to a Web Filter).
    // It allows observing or modifying both the request and the response.
    private static class LoggingAdvisor implements CallAdvisor {

        private static final Logger log = LoggerFactory.getLogger(LoggingAdvisor.class);

        @Override
        public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
            log.info("request: {}", request);
        
            // before call
            var response = chain.nextCall(request);

            // after call
            log.info("response: {}", response);

            return response;
        }

        @Override
        public String getName() {
            return this.getClass().getSimpleName();
        }

        @Override // Advisors execute in ascending order of their order value.
        public int getOrder() {
            return 100;
        }

    }

}
