package com.univade.ai.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestGenerationResult {
    private String sessionId;
    private String conversationId;
    private boolean newConversation;
    private TestMetadata metadata;
    @JsonProperty("test_class")
    private TestClass testClass;
    @JsonProperty("test_summary")
    private TestSummary testSummary;
    @JsonProperty("quality_checklist")
    private QualityChecklist qualityChecklist;
    private LocalDateTime generatedAt;
    private String status;
    private String errorMessage;

    public TestGenerationResult() {
        this.generatedAt = LocalDateTime.now();
        this.status = "SUCCESS";
    }

    public TestGenerationResult(String sessionId) {
        this();
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public boolean isNewConversation() { return newConversation; }
    public void setNewConversation(boolean newConversation) { this.newConversation = newConversation; }

    public TestMetadata getMetadata() { return metadata; }
    public void setMetadata(TestMetadata metadata) { this.metadata = metadata; }

    public TestClass getTestClass() { return testClass; }
    public void setTestClass(TestClass testClass) { this.testClass = testClass; }

    public TestSummary getTestSummary() { return testSummary; }
    public void setTestSummary(TestSummary testSummary) { this.testSummary = testSummary; }

    public QualityChecklist getQualityChecklist() { return qualityChecklist; }
    public void setQualityChecklist(QualityChecklist qualityChecklist) { this.qualityChecklist = qualityChecklist; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getGeneratedTest() {
        return testClass != null ? testClass.getSourceCode() : null;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestMetadata {
        @JsonProperty("component_type")
        private String componentType;
        @JsonProperty("entity_name")
        private String entityName;
        @JsonProperty("test_count")
        private int testCount;
        @JsonProperty("coverage_areas")
        private List<String> coverageAreas;
        private List<String> dependencies;
        @JsonProperty("test_framework")
        private String testFramework;
        @JsonProperty("assertion_library")
        private String assertionLibrary;

        // Getters and Setters
        public String getComponentType() { return componentType; }
        public void setComponentType(String componentType) { this.componentType = componentType; }

        public String getEntityName() { return entityName; }
        public void setEntityName(String entityName) { this.entityName = entityName; }

        public int getTestCount() { return testCount; }
        public void setTestCount(int testCount) { this.testCount = testCount; }

        public List<String> getCoverageAreas() { return coverageAreas; }
        public void setCoverageAreas(List<String> coverageAreas) { this.coverageAreas = coverageAreas; }

        public List<String> getDependencies() { return dependencies; }
        public void setDependencies(List<String> dependencies) { this.dependencies = dependencies; }

        public String getTestFramework() { return testFramework; }
        public void setTestFramework(String testFramework) { this.testFramework = testFramework; }

        public String getAssertionLibrary() { return assertionLibrary; }
        public void setAssertionLibrary(String assertionLibrary) { this.assertionLibrary = assertionLibrary; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestClass {
        @JsonProperty("package")
        private String packageName;
        private List<String> imports;
        @JsonProperty("class_name")
        private String className;
        private List<String> annotations;
        @JsonProperty("source_code")
        private String sourceCode;

        // Getters and Setters
        public String getPackageName() { return packageName; }
        public void setPackageName(String packageName) { this.packageName = packageName; }

        public List<String> getImports() { return imports; }
        public void setImports(List<String> imports) { this.imports = imports; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public List<String> getAnnotations() { return annotations; }
        public void setAnnotations(List<String> annotations) { this.annotations = annotations; }

        public String getSourceCode() { return sourceCode; }
        public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestSummary {
        @JsonProperty("happy_path_tests")
        private int happyPathTests;
        @JsonProperty("edge_case_tests")
        private int edgeCaseTests;
        @JsonProperty("error_condition_tests")
        private int errorConditionTests;
        @JsonProperty("validation_tests")
        private int validationTests;

        // Getters and Setters
        public int getHappyPathTests() { return happyPathTests; }
        public void setHappyPathTests(int happyPathTests) { this.happyPathTests = happyPathTests; }

        public int getEdgeCaseTests() { return edgeCaseTests; }
        public void setEdgeCaseTests(int edgeCaseTests) { this.edgeCaseTests = edgeCaseTests; }

        public int getErrorConditionTests() { return errorConditionTests; }
        public void setErrorConditionTests(int errorConditionTests) { this.errorConditionTests = errorConditionTests; }

        public int getValidationTests() { return validationTests; }
        public void setValidationTests(int validationTests) { this.validationTests = validationTests; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QualityChecklist {
        @JsonProperty("all_tests_executable")
        private boolean allTestsExecutable;
        @JsonProperty("proper_mocking")
        private boolean properMocking;
        @JsonProperty("comprehensive_assertions")
        private boolean comprehensiveAssertions;
        @JsonProperty("follows_naming_conventions")
        private boolean followsNamingConventions;
        @JsonProperty("includes_edge_cases")
        private boolean includesEdgeCases;
        @JsonProperty("handles_error_conditions")
        private boolean handlesErrorConditions;

        // Getters and Setters
        public boolean isAllTestsExecutable() { return allTestsExecutable; }
        public void setAllTestsExecutable(boolean allTestsExecutable) { this.allTestsExecutable = allTestsExecutable; }

        public boolean isProperMocking() { return properMocking; }
        public void setProperMocking(boolean properMocking) { this.properMocking = properMocking; }

        public boolean isComprehensiveAssertions() { return comprehensiveAssertions; }
        public void setComprehensiveAssertions(boolean comprehensiveAssertions) { this.comprehensiveAssertions = comprehensiveAssertions; }

        public boolean isFollowsNamingConventions() { return followsNamingConventions; }
        public void setFollowsNamingConventions(boolean followsNamingConventions) { this.followsNamingConventions = followsNamingConventions; }

        public boolean isIncludesEdgeCases() { return includesEdgeCases; }
        public void setIncludesEdgeCases(boolean includesEdgeCases) { this.includesEdgeCases = includesEdgeCases; }

        public boolean isHandlesErrorConditions() { return handlesErrorConditions; }
        public void setHandlesErrorConditions(boolean handlesErrorConditions) { this.handlesErrorConditions = handlesErrorConditions; }
    }
}
