package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec10PromptTemplateTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec10PromptTemplateTest.class);

    @BeforeAll
    public void setup() {
        this.chatClient = this.builder.defaultSystem("You are a customer support assistant. Limit responses to 2 sentences.")
                                      .defaultUser("Write a reply to a customer about {issue}. Keep the tone {tone}.")
                                      .build();
    }

    @ParameterizedTest
    @CsvSource({
            "delayed delivery, apologetic",
            "wrong item received, reassuring",
            "refund not processed, professional",
            "account locked, empathetic"
    })
    public void customerSupportReply(String issue, String tone) {
        log.info("issue: {}, tone: {}", issue, tone);
        var response = this.chatClient.prompt()
                                    .user(template -> template.param("issue", issue)
                                                                             .param("tone", tone))
                                    .call()
                                    .content();
        log.info("response: {}", response);
    }
}