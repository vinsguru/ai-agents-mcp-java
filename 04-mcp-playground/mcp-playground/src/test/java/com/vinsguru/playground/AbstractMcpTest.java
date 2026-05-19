package com.vinsguru.playground;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractMcpTest {

    private static final Logger log = LoggerFactory.getLogger(AbstractMcpTest.class);

    @LocalServerPort
    protected Integer port;

    @Autowired
    protected JsonMapper mapper;

    protected McpSyncClient mcpClient;

    protected McpClientTransport createTransport() {
        return HttpClientStreamableHttpTransport.builder("http://localhost:" + port)
                                                .endpoint("/mcp")
                                                .build();
    }

    protected <T> T readToolResponse(McpSchema.CallToolResult result, TypeReference<T> typeReference) {
        var content = (McpSchema.TextContent) result.content().getFirst();
        log.info("response: {}", content);
        return mapper.readValue(content.text(), typeReference);
    }

    @AfterAll
    public void closeClient() {
        mcpClient.closeGracefully();
    }

}
