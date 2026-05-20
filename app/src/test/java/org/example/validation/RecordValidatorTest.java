package org.example.validation;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.cleaning.RecordCleaner;
import org.example.model.PatientRecord;


public class RecordValidatorTest {
    
    @Test 
    void testTest(){
        assertTrue(true);
    }
    @Test
    void shouldDetectInvalidSpo2() {
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

        RecordValidator validator = new RecordValidator(); 
        RecordCleaner cleaner = new RecordCleaner(); 
        PatientRecord cleaned = cleaner.clean(record); 
        // act 
        List<ValidationIssue> issues = validator.validate(record, cleaned); 

        // assert 
        assertFalse(issues.isEmpty()); 
    }

    @Test
    void shouldDetectOneIssueForSpo2() {
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

        RecordValidator validator = new RecordValidator(); 
        RecordCleaner cleaner = new RecordCleaner(); 
        PatientRecord cleaned = cleaner.clean(record); 
        // act 
        List<ValidationIssue> issues = validator.validate(record, cleaned); 

        // assert expect one issue for spo2
        assertEquals(1, issues.size());
    }
}
