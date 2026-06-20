package com.vinsguru.playground.sec10.server.prompt;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpComplete;
import org.springframework.ai.mcp.annotation.McpPrompt;

import java.util.List;
import java.util.Map;

public class CodebasePrompts {

    private static final String CODE_MESSAGE = """
            Code:
            
            %s
            """;
    private final String codeReviewMessage;
    private final String generateTestsMessage;
    private final Map<String, String> sourceFiles;

    public CodebasePrompts(String codeReviewMessage, String generateTestsMessage, Map<String, String> sourceFiles) {
        this.codeReviewMessage = codeReviewMessage;
        this.generateTestsMessage = generateTestsMessage;
        this.sourceFiles = sourceFiles;
    }

    @McpPrompt(name = "code-review", description = "Review Java code for bugs, security, and performance")
    public McpSchema.GetPromptResult codeReview(@McpArg(name = "filename", description = "Java File Name", required = true) String filename) {
        return McpSchema.GetPromptResult.builder(
                List.of(
                        buildPromptMessage(McpSchema.Role.USER, codeReviewMessage),
                        buildPromptMessage(McpSchema.Role.USER, CODE_MESSAGE.formatted(sourceFiles.get(filename)))
                )
        ).build();
    }

    @McpPrompt(name = "generate-tests", description = "Generate unit tests for Java code")
    public McpSchema.GetPromptResult generateTests(@McpArg(name = "filename", description = "Java File Name", required = true) String filename) {
        return McpSchema.GetPromptResult.builder(
                List.of(
                        buildPromptMessage(McpSchema.Role.USER, generateTestsMessage),
                        buildPromptMessage(McpSchema.Role.USER, CODE_MESSAGE.formatted(sourceFiles.get(filename)))
                )
        ).build();
    }

    /*
    * Use McpSchema.CompleteRequest when the prompt expects multiple arguments
    * */
    @McpComplete(prompt = "code-review")
    public List<String> codeReviewSuggestions(String prefix) {
        return this.fetchSuggestions(prefix);
    }

    /*
     * Use McpSchema.CompleteRequest when the prompt expects multiple arguments
     * */
    @McpComplete(prompt = "generate-tests")
    public List<String> generateTestSuggestions(String prefix) {
        return this.fetchSuggestions(prefix);
    }

    private List<String> fetchSuggestions(String prefix){
        return this.sourceFiles.keySet()
                               .stream()
                               .filter(name -> name.startsWith(prefix))
                               .toList();
    }

    private McpSchema.PromptMessage buildPromptMessage(McpSchema.Role role, String content){
        return McpSchema.PromptMessage.builder(
                role,
                McpSchema.TextContent.builder(content).build()
        ).build();
    }

}
