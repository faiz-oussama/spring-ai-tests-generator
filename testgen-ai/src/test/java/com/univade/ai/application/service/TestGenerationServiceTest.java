package com.univade.ai.application.service;

import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.model.TestGenerationResult;
import com.univade.ai.domain.repository.MemoryRepository;
import com.univade.ai.domain.service.PromptRefiner;
import com.univade.ai.domain.service.TestResultProcessor;
import com.univade.ai.infrastructure.ai.OpenAiClient;
import com.univade.ai.infrastructure.file.TestFileManager;
import com.univade.ai.infrastructure.prompt.PromptLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestGenerationService Tests")
class TestGenerationServiceTest {

    @Mock
    private OpenAiClient openAiClient;
    
    @Mock
    private PromptLoader promptLoader;
    
    @Mock
    private PromptRefiner promptRefiner;
    
    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private TestFileManager testFileManager;

    @Mock
    private TestResultProcessor testResultProcessor;

    private TestGenerationService testGenerationService;
    
    @BeforeEach
    void setUp() {
        testGenerationService = new TestGenerationService(
            openAiClient,
            promptLoader,
            promptRefiner,
            memoryRepository,
            testFileManager,
            testResultProcessor
        );
    }
    
    @Test
    @DisplayName("Should build context with valid inputs")
    void shouldBuildContextWithValidInputs() {
        // Given
        String userInput = "Generate tests for a user service";
        String sessionId = "test-session";

        // When
        PromptContext context = testGenerationService.buildContext(userInput, sessionId);

        // Then
        assertThat(context).isNotNull();
        assertThat(context.getUserInput()).isEqualTo(userInput);
        assertThat(context.getSessionId()).isEqualTo(sessionId);
    }

    @Test
    @DisplayName("Should generate session ID when not provided")
    void shouldGenerateSessionIdWhenNotProvided() {
        // Given
        String userInput = "Generate tests for a repository class";

        // When
        PromptContext context = testGenerationService.buildContext(userInput, null);

        // Then
        assertThat(context).isNotNull();
        assertThat(context.getSessionId()).isNotNull();
        assertThat(context.getSessionId()).isNotEmpty();
    }

    @Test
    @DisplayName("Should generate tests successfully")
    void shouldGenerateTestsSuccessfully() {
        // Given
        PromptContext context = new PromptContext("session-1", "Generate tests for a service class");
        TestGenerationResult expectedResult = new TestGenerationResult("session-1");
        expectedResult.setStatus("SUCCESS");

        when(promptLoader.loadSystemPrompt()).thenReturn("System prompt");
        when(promptRefiner.buildUserPrompt(any(PromptContext.class))).thenReturn("User prompt");
        when(openAiClient.generateTests(anyString(), anyString())).thenReturn(expectedResult);

        // When
        TestGenerationResult result = testGenerationService.generateTests(context);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
        assertThat(result.getSessionId()).isEqualTo("session-1");
    }
}
