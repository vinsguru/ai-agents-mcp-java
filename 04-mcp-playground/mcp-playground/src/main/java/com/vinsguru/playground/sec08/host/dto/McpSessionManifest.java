package com.vinsguru.playground.sec08.host.dto;

import java.util.List;

public record McpSessionManifest(String modelName,
                                 List<String> availableTools,
                                 List<String> suggestedInputs) {
}
