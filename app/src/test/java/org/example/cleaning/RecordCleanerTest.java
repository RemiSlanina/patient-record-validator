package org.example.cleaning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.example.model.PatientRecord;
import org.junit.jupiter.api.Test;

public class RecordCleanerTest {
    private final RecordCleaner cleaner = new RecordCleaner(); // don't change, actually

    @Test
    void clean_shouldTrimStrings(){
        // arrange 
        PatientRecord raw = new PatientRecord(
            "P-1001   ", 
            3, 
            36.8, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "     "
        );
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 
        // assert
        assertEquals("P-1001", cleaned.patientId); 
        assertNull(cleaned.userId); 
    }

    @Test
    void clean_shouldRoundTemperature(){
        // arrange 
        PatientRecord raw = new PatientRecord(
            "P-1001", 
            3, 
            36.8888, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(55.0), 
            "friday"
        );
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 
        // assert
        assertEquals(36.9, cleaned.temperature);
    }

    @Test
    void clean_dropInvalidWeights(){
        // arrange 
        PatientRecord raw = new PatientRecord(
            "P-1001", 
            3, 
            36.8, 
            70, 
            "2026-05-14T03:55:00", 
            List.of(82.0, -1.0, 83.9), 
            "friday"
        );
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 
        // assert
        assertEquals(List.of(82.0, 83.9), cleaned.weights);
    }

    @Test
    void clean_roundTemperatureUpToSingleDecimal(){
        PatientRecord raw = new PatientRecord(); 
        raw.temperature = 36.45; 
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 
        // assert 
        assertEquals(36.5, cleaned.temperature);
    }

    @Test
    void clean_keepNullTemperatureNull(){
        PatientRecord raw = new PatientRecord(); 
        raw.temperature = null; 
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 

        assertNull(cleaned.temperature);
    }

    @Test
    void clean_keepInvalidDateTimeAfterTrim(){
        PatientRecord raw = new PatientRecord(); 
        raw.dateTimeTaken = "   Monday   "; 
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 

        assertEquals("Monday", cleaned.dateTimeTaken);
    }
    
    @Test
    void clean_returnNullForNullWeightsInput(){
        PatientRecord raw = new PatientRecord(); 
        raw.weights = null; 
        // act 
        PatientRecord cleaned = cleaner.clean(raw); 

        assertNull(cleaned.weights);
    }

}
