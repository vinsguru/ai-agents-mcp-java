package com.vinsguru.playground.sec01.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class SimpleTools {

    private static final Logger log = LoggerFactory.getLogger(SimpleTools.class);

    @McpTool(name = "generate-random-number", description = "Generate a random number")
    public Integer generateRandomNumber() {
        var random = ThreadLocalRandom.current().nextInt(1, 1000);
        log.info("generated random: {}", random);
        return random;
    }

    @McpTool(description = "Save the text content to a file")
    public void saveContentToFile(String text) {
        log.info("saving content: {}", text);
    }

}
