package org.example.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class JsonPreValidatorTest {
    @Test
    void testInvalidNonNumericStrings(){
        String json = "[{\"spo2\": \"error\", \"temperature\": \"invalid\"}]";

        List<ValidationIssue> issues = JsonPreValidator.validateJsonTypes(json); 
        assertEquals(2, issues.size(), "Invalid strings should trigger errors");
        assertTrue(issues.stream().anyMatch(i -> i.field.equals(("spo2"))));
        assertTrue(issues.stream().anyMatch(i -> i.field.equals("temperature")));
    }

    @Test 
    void testValidNumericString(){
        String json = "[{\"spo2\": \"98.1\", \"temperature\": \"34.8\"}]";
        List<ValidationIssue> issues = JsonPreValidator.validateJsonTypes(json); 
        assertTrue(issues.isEmpty(), "Valid numeric strings shouldn't trigger any errors.");
    }

    @Test
    void testMixedValidAndInvald(){
        String json = "[{\"spo2\": \"99\", \"temperature\": \"thermometer broken\"}]";
        List<ValidationIssue> issues = JsonPreValidator.validateJsonTypes(json); 
        assertEquals(1, issues.size(), "Only errors for invalid fields.");
        assertEquals("temperature", issues.getFirst().field);
    }

    @Test 
    void reportInvalidJsonMissingQuotes(){
        String json = "[{invalid json}]"; 
        List<ValidationIssue> issues = JsonPreValidator.validateJsonTypes(json); 
        assertEquals(1, issues.size(), "1 issue exactly for detecting json is invalid");
        assertEquals("JSON", issues.getFirst().field);
        assertEquals(ValidationIssue.Severity.ERROR, issues.getFirst().severity);
        assertTrue(issues.getFirst().message.contains("Failed to parse JSON "));
    }

    @Test 
    void reportInvalidJsonMissingBrackets(){
        String json = "[{\"invalid\":\"json\""; 
        List<ValidationIssue> issues = JsonPreValidator.validateJsonTypes(json); 
        assertEquals(1, issues.size(), "1 issue exactly for detecting json is invalid");
        assertEquals("JSON", issues.getFirst().field);
        assertEquals(ValidationIssue.Severity.ERROR, issues.getFirst().severity);
        assertTrue(issues.getFirst().message.contains("Failed to parse JSON "));
    }
}
