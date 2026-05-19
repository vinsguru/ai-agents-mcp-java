package com.vinsguru.playground;

import com.vinsguru.playground.sec08.SectionRunner;
import com.vinsguru.playground.sec08.server.dto.ToolResult;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import static io.modelcontextprotocol.spec.McpSchema.ElicitResult.Action.*;

@SpringBootTest(
        classes = SectionRunner.Server.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "section=sec08",
                "config=server"
        }
)
class Lec03ElicitRequestTest extends AbstractMcpTest {

    private BlockingQueue<McpSchema.ElicitRequest> elicitRequestsQueue;
    private BlockingQueue<McpSchema.ElicitResult> elicitResultsQueue;

    @BeforeAll
    public void initialize() {
        elicitRequestsQueue = new LinkedBlockingQueue<>();
        elicitResultsQueue = new LinkedBlockingQueue<>();
        mcpClient = McpClient.sync(createTransport())
                                  .elicitation(elicitRequest -> {
                                      elicitRequestsQueue.add(elicitRequest);
                                      return elicitResultsQueue.poll();
                                  })
                                  .build();
    }

    @ParameterizedTest
    @MethodSource("elicitResultScenarios")
    void elicitRequest(McpSchema.ElicitResult elicitResult, String expectedMessage) {
        // enqueue the elicitation result for the handler
        elicitResultsQueue.add(elicitResult);

        // invoke the MCP tool
        var request = McpSchema.CallToolRequest.builder()
                                               .name("deleteFile")
                                               .arguments(Map.of("fileName", "file1.txt"))
                                               .progressToken(1)
                                               .build();
        var result = mcpClient.callTool(request);

        // validate the elicitation request sent by the MCP server
        var elicitRequest = elicitRequestsQueue.poll();
        Assertions.assertNotNull(elicitRequest);
        Assertions.assertEquals("Enter verification code to delete 'file1.txt'", elicitRequest.message());

        // the elicitation result should have been consumed and the queue should be empty
        Assertions.assertTrue(elicitResultsQueue.isEmpty());

        // validate the tool result
        var toolResult = readToolResponse(result, new TypeReference<ToolResult>() {
        });
        Assertions.assertEquals(expectedMessage, toolResult.message());
    }

    public Stream<Arguments> elicitResultScenarios() {
        return Stream.of(
                Arguments.of(buildElicitResult(ACCEPT, "1234"), "File deleted successfully"),
                Arguments.of(buildElicitResult(ACCEPT, "1111"), "Provided verification code is not valid. File not deleted"),
                Arguments.of(buildElicitResult(DECLINE, ""), "File deletion cancelled"),
                Arguments.of(buildElicitResult(CANCEL, ""), "File deletion cancelled")
        );
    }

    private McpSchema.ElicitResult buildElicitResult(McpSchema.ElicitResult.Action action, String code) {
        return McpSchema.ElicitResult.builder()
                                     .message(action)
                                     .content(Map.of("code", code))
                                     .build();
    }

}
