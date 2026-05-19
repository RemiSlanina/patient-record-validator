package org.example.cleaning;

import org.example.model.PatientRecord;

public class RecordCleaner {
    
    public PatientRecord clean(PatientRecord source){

        if (source == null) return null; 
        PatientRecord cleaned = new PatientRecord(); 
        // cleaned.patientId = source.patientId.trim(); 
        cleaned.patientId = trimToNull(source.patientId); 
        cleaned.userId = trimToNull((source.userId)); 
        System.out.println("cleaned userID: " + cleaned.userId);
        return cleaned; 
    }

    // Helpers 
    private String trimToNull(String value) {
        if (value == null) {
            return null; 
        }
        String trimmed = value.trim(); 
        return trimmed.isEmpty() ? null : trimmed; 
    }
}
