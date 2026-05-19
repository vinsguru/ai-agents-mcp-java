package com.vinsguru.playground.sec08.server.tools;

import com.vinsguru.playground.sec08.server.dto.ToolResult;
import com.vinsguru.playground.sec08.server.dto.VerificationCode;
import com.vinsguru.playground.sec08.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.ai.mcp.annotation.context.StructuredElicitResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FileTools {

    private static final Logger log = LoggerFactory.getLogger(FileTools.class);
    private final FileService fileService;

    public FileTools(FileService fileService) {
        this.fileService = fileService;
    }

    @McpTool(description = "List all the files")
    public List<String> listFiles() throws IOException {
        log.info("listing files");
        return this.fileService.listFiles();
    }

    @McpTool(description = "Delete a file")
    public ToolResult deleteFile(McpSyncRequestContext context,
                                 @McpToolParam(description = ParamDocs.FILE_NAME) String fileName) throws IOException {
        log.info("delete file {}", fileName);

        // Wait for user response
        // This call blocks the current thread. Use virtual threads for scalability.
        // spring.threads.virtual.enabled=true
        var result = this.requestVerificationCodeForDeletion(context, fileName);
        return switch (result.action()){
            case ACCEPT -> this.deleteFile(fileName, result.structuredContent().code());
            case null, default -> new ToolResult("File deletion cancelled");
        };
    }

    private StructuredElicitResult<VerificationCode> requestVerificationCodeForDeletion(McpSyncRequestContext context, String fileName) {
        var result = context.elicit(
                spec -> spec.message("Enter verification code to delete '%s'".formatted(fileName))
                                             .meta("progressToken", context.request().progressToken()), // https://github.com/spring-projects/spring-ai/issues/5050
                                              VerificationCode.class
        );
        log.info("elicit result: {}", result);
        return result;
    }

    private ToolResult deleteFile(String fileName, String code) throws IOException {
        if("1234".equals(code)){
            this.fileService.deleteFile(fileName);
            return new ToolResult("File deleted successfully");
        }
        return new ToolResult("Provided verification code is not valid. File not deleted");
    }

    private static class ParamDocs {
        static final String FILE_NAME = "File name with extension (e.g., notes.txt)";
    }

}
