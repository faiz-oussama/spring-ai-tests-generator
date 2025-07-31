package com.univade.ai.infrastructure.file;

import com.univade.ai.domain.model.TestGenerationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TestFileManager {
    
    private static final Logger logger = LoggerFactory.getLogger(TestFileManager.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    @Value("${testgen.output.directory:generated-tests}")
    private String outputDirectory;
    
    public String saveGeneratedTest(TestGenerationResult result) {
        try {
            Path baseDir = createDirectoryStructure(result);
            String filename = generateFilename(result);
            Path testFilePath = baseDir.resolve(filename);

            String testContent = extractTestContent(result);
            Files.writeString(testFilePath, testContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            saveMetadata(result, baseDir);

            logger.info("Saved generated test to: {}", testFilePath.toAbsolutePath());
            return testFilePath.toAbsolutePath().toString();

        } catch (IOException e) {
            logger.error("Failed to save generated test for session: {}", result.getSessionId(), e);
            throw new RuntimeException("Failed to save generated test", e);
        }
    }
    
    private Path createDirectoryStructure(TestGenerationResult result) throws IOException {
        Path baseOutputDir = Paths.get(outputDirectory);
        Files.createDirectories(baseOutputDir);

        String sessionDir = result.getSessionId() != null ? result.getSessionId() : "unknown-session";
        Path sessionPath = baseOutputDir.resolve(sessionDir);
        Files.createDirectories(sessionPath);

        if (result.getMetadata() != null && result.getMetadata().getComponentType() != null) {
            String componentType = result.getMetadata().getComponentType().toLowerCase();
            Path componentPath = sessionPath.resolve(componentType);
            Files.createDirectories(componentPath);
            return componentPath;
        }

        return sessionPath;
    }
    
    private String generateFilename(TestGenerationResult result) {
        StringBuilder filename = new StringBuilder();

        if (result.getMetadata() != null && result.getMetadata().getEntityName() != null) {
            String entityName = result.getMetadata().getEntityName();
            filename.append(entityName);
            if (!entityName.endsWith("Test")) {
                filename.append("Test");
            }
        } else {
            filename.append("GeneratedTest");
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        filename.append("_").append(timestamp).append(".java");

        return filename.toString();
    }
    
    private String extractTestContent(TestGenerationResult result) {
        if (result.getTestClass() != null && result.getTestClass().getSourceCode() != null) {
            return result.getTestClass().getSourceCode();
        }

        StringBuilder content = new StringBuilder();
        content.append("// Generated Test - Session: ").append(result.getSessionId()).append("\n");
        content.append("// Status: ").append(result.getStatus()).append("\n");
        content.append("// Generated At: ").append(result.getGeneratedAt()).append("\n\n");

        if (result.getErrorMessage() != null) {
            content.append("// ERROR: ").append(result.getErrorMessage()).append("\n");
        }

        return content.toString();
    }
    
    private void saveMetadata(TestGenerationResult result, Path directory) throws IOException {
        Path metadataPath = directory.resolve("metadata.json");

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"sessionId\": \"").append(result.getSessionId()).append("\",\n");
        json.append("  \"status\": \"").append(result.getStatus()).append("\",\n");
        json.append("  \"generatedAt\": \"").append(result.getGeneratedAt()).append("\"");

        if (result.getMetadata() != null) {
            TestGenerationResult.TestMetadata meta = result.getMetadata();
            json.append(",\n  \"componentType\": \"").append(meta.getComponentType()).append("\"");
            json.append(",\n  \"entityName\": \"").append(meta.getEntityName()).append("\"");
            json.append(",\n  \"testCount\": ").append(meta.getTestCount());
        }

        if (result.getErrorMessage() != null) {
            json.append(",\n  \"errorMessage\": \"").append(result.getErrorMessage().replace("\"", "\\\"")).append("\"");
        }

        json.append("\n}");

        Files.writeString(metadataPath, json.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public Path getOutputDirectory() {
        return Paths.get(outputDirectory);
    }
    
    public void createOutputDirectoryIfNotExists() throws IOException {
        Path dir = Paths.get(outputDirectory);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            logger.info("Created output directory: {}", dir.toAbsolutePath());
        }
    }
}
