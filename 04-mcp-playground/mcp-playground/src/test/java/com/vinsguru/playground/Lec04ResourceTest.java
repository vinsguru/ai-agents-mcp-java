package com.vinsguru.playground;

import com.vinsguru.playground.sec09.SectionRunner;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(
        classes = SectionRunner.Server.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "section=sec09",
                "config=server"
        }
)
class Lec04ResourceTest extends AbstractMcpTest {

    @BeforeAll
    public void initialize() {
        mcpClient = McpClient.sync(createTransport())
                             .build();
    }

    @ParameterizedTest
    @MethodSource("readResourceScenarios")
    void readResource(String uri, int expectedSize) {
        var request = new McpSchema.ReadResourceRequest(uri);
        var response = mcpClient.readResource(request);
        Assertions.assertEquals(expectedSize, response.contents().size());
    }

    public Stream<Arguments> readResourceScenarios() {
        return Stream.of(
                Arguments.of("jobs://positions", 22),
                Arguments.of("jobs://positions/1", 1),
                Arguments.of("jobs://positions/skill/Java", 6),
                Arguments.of("jobs://positions/location/Remote", 9)
        );
    }

}
