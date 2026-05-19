package org.example.cleaning;

import org.example.model.PatientRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List; 

public class RecordCleaner {
    
    public PatientRecord clean(PatientRecord source){

        if (source == null) return null; 
        PatientRecord cleaned = new PatientRecord(); 
        // cleaned.patientId = source.patientId.trim(); 
        cleaned.patientId = trimToNull(source.patientId); 
        cleaned.userId = trimToNull((source.userId)); 
        cleaned.spo2 = source.spo2; 
        cleaned.temperature = cleanTemperature(source.temperature); 
        cleaned.heartRate = source.heartRate; 
        cleaned.weights = normalizeWeights(source.weights); 
        cleaned.dateTimeTaken = normalizeDateTime(source.dateTimeTaken); 

        // System.out.println("cleaned datetime: " + cleaned.dateTimeTaken);
        // System.out.println("test temp " + Math.round(36.456 * 100.0) / 100.0);
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

    private Double cleanTemperature(Double tempDouble){
        if (tempDouble == null) return null; 

        // round to one decimal place 
        return (double) Math.round(tempDouble * 10.0)/ 10.0; 
    }

    private List<Double> normalizeWeights(List<Double> weights){
        if (weights == null) {
            return null; 
        }
        List<Double> cleanedWeights = new ArrayList<>(); 
        for (Double weight : weights){
            if (weight == null) continue; 

            if (weight > 0) {
                cleanedWeights.add(weight); 
            }
            // cleanedWeights.add(Math.abs(weight)); // no
        }
        return cleanedWeights; 
    }

    private String normalizeDateTime(String dateTimeTaken) {
        if (dateTimeTaken == null) {
            return null; 
        }
        String trimmed = dateTimeTaken.trim(); 
        if (trimmed == null) {
            return null; 
        }
        try {
            return LocalDateTime.parse(trimmed).toString(); 
        } catch (Exception e) {
            return trimmed; 
        }
    }
}
