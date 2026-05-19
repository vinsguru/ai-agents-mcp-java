package com.vinsguru.playground.sec02.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
public class FileTools {

    private static final Logger log = LoggerFactory.getLogger(FileTools.class);
    private final Path basePath;

    // At runtime, it actually points to the compiled output — target/classes.
    public FileTools(@Value("classpath:${section}/files") Resource resource) throws IOException {
        this.basePath = resource.getFilePath();
    }

    @McpTool(description = "List all the files")
    public List<String> listFiles() throws IOException {
        log.info("listing files");
        try (var stream = Files.list(basePath)) {
            return stream.map(p -> p.getFileName().toString())
                         .toList();
        }
    }

    @McpTool(description = "Read the full content of a file")
    public String readFile(@McpToolParam(description = ParamDocs.FILE_NAME) String fileName) throws IOException {
        log.info("reading file:{}", fileName);
        var path = resolveSafePath(fileName);
        return Files.readString(path);
    }

    @McpTool(description = "Save, create or append content to a file")
    public void writeToFile(
            @McpToolParam(description = ParamDocs.FILE_NAME) String fileName,
            @McpToolParam(description = ParamDocs.CONTENT) String content
    ) throws IOException {
        log.info("appending {} ==> file: {}", content, fileName);
        var path = resolveSafePath(fileName);
        Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    @McpTool(description = "Delete a file")
    public void deleteFile(@McpToolParam(description = ParamDocs.FILE_NAME) String fileName) throws IOException {
        log.info("deleting file:{}", fileName);
        var path = resolveSafePath(fileName);
        Files.deleteIfExists(path);
    }

    private Path resolveSafePath(String fileName) {
        var path = basePath.resolve(fileName).normalize();
        if (!path.startsWith(basePath)) {
            throw new IllegalArgumentException("Invalid file path");
        }
        return path;
    }

    private static class ParamDocs {
        static final String FILE_NAME = "File name with extension (e.g., notes.txt)";
        static final String CONTENT = "Text content to be written to the file";
    }

}
