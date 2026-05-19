package com.vinsguru.playground.sec08.server.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private final Path basePath;

    public FileService() throws IOException {
        this.basePath = Paths.get(System.getProperty("java.io.tmpdir"), "elicit-demo-files");
        this.initialize();
    }

    public List<String> listFiles() throws IOException {
        try (var stream = Files.list(basePath)) {
            return stream.map(p -> p.getFileName().toString())
                         .toList();
        }
    }

    public void deleteFile(String fileName) throws IOException {
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

    private void initialize() throws IOException {
        Files.createDirectories(basePath);
        for (int i = 1; i <= 5; i++) {
            var file = basePath.resolve("file%d.txt".formatted(i));
            if (Files.notExists(file)) {
                Files.writeString(file, "Sample content for " + file.getFileName());
            }
        }
    }

}
