package com.vinsguru.playground.sec10.server.config;

import com.vinsguru.playground.sec10.server.prompt.CodebasePrompts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class ServerConfiguration {

    @Bean
    public CodebasePrompts codebasePrompts(@Value("classpath:${section}/") Resource resource) throws IOException {
        var path = resource.getFilePath();
        var files = this.loadSourceFiles(path.resolve("java-class-files"));
        var codeReviewMessage = Files.readString(path.resolve("prompt-messages/code-review.txt"));
        var generateTestsMessage = Files.readString(path.resolve("prompt-messages/generate-tests.txt"));
        return new CodebasePrompts(codeReviewMessage, generateTestsMessage, files);
    }

    private Map<String, String> loadSourceFiles(Path sourceDirectory) throws IOException {
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            return stream.filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toMap(
                            path -> path.getFileName().toString(),
                            path -> {
                                try {
                                    return Files.readString(path);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ));
        }
    }

}
