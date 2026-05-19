package com.vinsguru.playground.sec02;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

import java.util.Optional;

public class Lec02AdvisorParamsTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02AdvisorParamsTest.class);

    @BeforeAll
    public void setup() {
        this.chatClient = this.builder.defaultAdvisors(new LoggingAdvisor())
                                      .build();
    }

    @ParameterizedTest
    @ValueSource(booleans = {
            true, false
    })
    public void advisorParams(boolean logEnabled) {
        var response = this.chatClient.prompt("3 + 2 = ?")
                                      .advisors(spec -> spec.param("logEnabled", logEnabled))
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
            var logEnabled = Optional.ofNullable(request.context().get("logEnabled"))
                                           .map(Object::toString)
                                           .map(Boolean::parseBoolean)
                                           .orElse(false);

            if (logEnabled) {
                log.info("request: {}", request);
            }

            // before call
            var response = chain.nextCall(request);

            // after call
            if (logEnabled) {
                log.info("response: {}", response);
            }

            return response;
        }

        @Override
        public String getName() {
            return this.getClass().getSimpleName();
        }

        @Override
        public int getOrder() {
            return 100;
        }

    }

}
