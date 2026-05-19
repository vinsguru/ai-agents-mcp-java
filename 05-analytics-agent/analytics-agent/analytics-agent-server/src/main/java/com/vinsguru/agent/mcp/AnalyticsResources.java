package com.vinsguru.agent.mcp;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.annotation.McpResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AnalyticsResources {

    private final String ordersSchema;

    public AnalyticsResources(@Value("classpath:orders-schema.txt") Resource resource) throws Exception{
        this.ordersSchema = resource.getContentAsString(StandardCharsets.UTF_8);
    }

    @McpResource(uri = "analytics://schema/orders")
    public McpSchema.ReadResourceResult readOrdersSchema() {
        return new McpSchema.ReadResourceResult(List.of(
                new McpSchema.TextResourceContents(
                        "analytics://schema/orders",
                        "text/plain",
                        ordersSchema
                )
        ));
    }

}
