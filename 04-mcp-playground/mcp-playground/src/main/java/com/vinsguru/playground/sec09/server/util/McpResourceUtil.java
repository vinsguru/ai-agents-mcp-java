package com.vinsguru.playground.sec09.server.util;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.util.json.JsonParser;

public class McpResourceUtil {

    private static final String MIME_JSON = "application/json";
    private static final String MIME_TEXT = "text/plain";

    public static McpSchema.ResourceContents toJsonResource(String uri, Object object) {
        return new McpSchema.TextResourceContents(
                uri,
                MIME_JSON,
                JsonParser.toJson(object)
        );
    }

    public static McpSchema.ResourceContents toTextResource(String uri, String text) {
        return new McpSchema.TextResourceContents(
                uri,
                MIME_TEXT,
                text
        );
    }

}
