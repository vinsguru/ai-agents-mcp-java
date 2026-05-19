package com.vinsguru.playground;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.profiles.active=openai"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    @Autowired
    protected ChatClient.Builder builder;
    protected ChatClient chatClient;

    @BeforeAll
    public void setup(){
        this.chatClient = this.builder.build();
    }

}
