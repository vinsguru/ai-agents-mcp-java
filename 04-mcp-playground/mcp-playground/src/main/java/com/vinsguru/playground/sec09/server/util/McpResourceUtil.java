package com.vinsguru.playground.sec09.server.util;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.util.JacksonUtils;

public class McpResourceUtil {

    private static final String MIME_JSON = "application/json";
    private static final String MIME_TEXT = "text/plain";

    public static McpSchema.ResourceContents toJsonResource(String uri, Object object) {
        return McpSchema.TextResourceContents.builder(uri, JacksonUtils.getDefaultJsonMapper().writeValueAsString(object))
                                             .mimeType(MIME_JSON)
                                             .build();
    }

    public static McpSchema.ResourceContents toTextResource(String uri, String text) {
        return McpSchema.TextResourceContents.builder(uri, text)
                                             .mimeType(MIME_TEXT)
                                             .build();
    }

}
