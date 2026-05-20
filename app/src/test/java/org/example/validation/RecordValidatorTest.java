package org.example.validation;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.cleaning.RecordCleaner;
import org.example.model.PatientRecord;


public class RecordValidatorTest {
    private final RecordValidator validator = new RecordValidator(); 
    private final RecordCleaner cleaner = new RecordCleaner(); 
    
    @Test 
    void testTest(){
        assertTrue(true);
    }

    @Test
    void detectInvalidPatientId() {
        PatientRecord record = new PatientRecord(
            "Alberta", 
            96, 
            36.8, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "nurse-01"
        );
        PatientRecord cleaned = cleaner.clean(record); 
        List<ValidationIssue> issues = validator.validate(record, cleaned);
        ValidationIssue issue = issues.getFirst(); 
        assertEquals("patientId", issue.field); 
        assertEquals(ValidationIssue.Severity.WARNING, issue.severity);
        assertEquals(
            "Invalid patient ID format",
            issue.message
        );
    }

    @Test
    void detectInvalidTemperature() {
        PatientRecord record = new PatientRecord(
            "P-100110100", 
            96, 
            23.5, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "nurse-01"
        );
        PatientRecord cleaned = cleaner.clean(record); 
        List<ValidationIssue> issues = validator.validate(record, cleaned);
        ValidationIssue issue = issues.getFirst(); 
        assertEquals("temperature", issue.field); 
        assertEquals(ValidationIssue.Severity.WARNING, issue.severity);
        assertEquals(
            "Temperature out of range (35-42°C)",
            issue.message
        );
    }

    @Test
    void detectWeightsEqualsNull() {
        PatientRecord record = new PatientRecord(
            "P-100110100", 
            96, 
            36.5, 
            70, 
            "2026-05-14T03:55:00", 
            null, 
            "nurse-01"
        );
        PatientRecord cleaned = cleaner.clean(record); 
        List<ValidationIssue> issues = validator.validate(record, cleaned);
        ValidationIssue issue = issues.getFirst(); 
        assertEquals("weights", issue.field); 
        assertEquals(ValidationIssue.Severity.INFO, issue.severity);
        assertEquals(
            "Empty weight values",
            issue.message
        );
    }

    @Test
    void detectPatientIdEqualsUserId() {
        PatientRecord record = new PatientRecord(
            "P-0001", 
            96, 
            36.5, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.5, 55.6, 56.0), 
            "P-0001"
        );
        PatientRecord cleaned = cleaner.clean(record); 
        List<ValidationIssue> issues = validator.validate(record, cleaned);
        ValidationIssue issue = issues.getFirst(); 
        assertEquals("userId", issue.field); 
        assertEquals(ValidationIssue.Severity.WARNING, issue.severity);
        assertEquals(
            "userId matches patientId",
            issue.message
        );
    }

    @Test
    void detectInvalidSpo2() { 
        // arrange 
        PatientRecord record = new PatientRecord(
            "P-1001", 
            3, 
            36.8, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "Alberta"
        );

        PatientRecord cleaned = cleaner.clean(record); 
        // act 
        List<ValidationIssue> issues = validator.validate(record, cleaned); 

        // assert 
        ValidationIssue issue = issues.getFirst(); 
        assertEquals(ValidationIssue.Severity.WARNING, issue.severity);
        assertEquals("spo2", issue.field);
        assertEquals("SpO₂ out of range (95-100%)", issue.message);
    }


    @Test
    void detectEmptySpo2() {
        // arrange 
        PatientRecord record = new PatientRecord(
            "P-1001", 
            null, 
            36.8, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "Alberta"
        );

        PatientRecord cleaned = cleaner.clean(record); 
        List<ValidationIssue> issues = validator.validate(record, cleaned); 

        ValidationIssue issue = issues.getFirst(); 
        assertEquals(ValidationIssue.Severity.ERROR, issue.severity);
        assertEquals("spo2", issue.field);
        assertEquals( "Missing SpO₂ value", issue.message);
    }
}
