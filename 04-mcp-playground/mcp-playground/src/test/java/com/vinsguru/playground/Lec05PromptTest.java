package com.vinsguru.playground;

import com.vinsguru.playground.sec10.SectionRunner;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

@SpringBootTest(
        classes = SectionRunner.Server.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "section=sec10",
                "config=server"
        }
)
class Lec05PromptTest extends AbstractMcpTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05PromptTest.class);

    @BeforeAll
    public void initialize() {
        mcpClient = McpClient.sync(createTransport())
                             .build();
    }

    @Test
    void promptRequest() {
        var request = new McpSchema.GetPromptRequest("code-review", Map.of("filename", "UserService.java"));
        var result = mcpClient.getPrompt(request);
        log.info("result: {}", result);
        Assertions.assertEquals(2, result.messages().size());
    }

    @ParameterizedTest
    @MethodSource("promptCompletionScenarios")
    void completionRequest(String prefix, int expectedSuggestionsCount) {
        var request = new McpSchema.CompleteRequest(
                new McpSchema.PromptReference("code-review"),
                new McpSchema.CompleteRequest.CompleteArgument("filename", prefix)
        );
        var result = mcpClient.completeCompletion(request);
        log.info("values: {}", result.completion().values());
        Assertions.assertEquals(expectedSuggestionsCount, result.completion().values().size());
    }

    public Stream<Arguments> promptCompletionScenarios() {
        return Stream.of(
                Arguments.of("", 4),
                Arguments.of("A", 0),
                Arguments.of("U", 1),
                Arguments.of("u", 0), // case-sensitive
                Arguments.of("C", 1)
        );
    }
}
