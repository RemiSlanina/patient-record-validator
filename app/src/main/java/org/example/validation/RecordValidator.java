package org.example.validation;
import org.example.model.PatientRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class RecordValidator {
    public List<ValidationIssue> validate(PatientRecord record) {
        List<ValidationIssue> issues = new ArrayList<>(); 
        // test patientId
        ValidationIssue patientIdIssue = validatePatientId(record.patientId);
        if (patientIdIssue != null) issues.add(patientIdIssue);
        ValidationIssue spo2Issue = validateSpo2(record.spo2, record.patientId); 
        if (spo2Issue != null) issues.add(spo2Issue);
        ValidationIssue temperatureIssue = validateTemperature(record.temperature, record.patientId); 
        if (temperatureIssue != null) issues.add(temperatureIssue); 
        ValidationIssue heartRateIssue = validateHeartRate(record.heartRate, record.patientId); 
        if (heartRateIssue != null) issues.add(heartRateIssue); 
        ValidationIssue weightsIssue = validateWeights(record.weights, record.patientId); 
        if (weightsIssue != null) issues.add(weightsIssue); 
        ValidationIssue dateTimeTakenIssue = validateDateTimeTaken(record.dateTimeTaken, record.patientId); 
        if (dateTimeTakenIssue != null) issues.add(dateTimeTakenIssue); 
        ValidationIssue UserIdIssue = validateUserId(record.userId, record.patientId); 
        if (UserIdIssue != null) issues.add(UserIdIssue); 
        // TODO test the whole record later 
        return issues; 
    }



    private ValidationIssue validatePatientId(String patientId){
        if (patientId == null || patientId.isBlank()) {
            // TODO define a constructor for ValidationIssue
            ValidationIssue issue =  new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "patientId";
            issue.message = "Missing patient ID";
            issue.severity = ValidationIssue.Severity.INFO;
            return issue; 
        }
        if (!patientId.matches("^P-\\d+$")) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "patientId"; 
            issue.message = "Invalid patient ID format"; 
            issue.severity = ValidationIssue.Severity.INFO; 
            return issue; 
        }
        // no issues: 
        return null; 
    }

    private ValidationIssue validateSpo2(Integer spo2, String patientId){
        // the normal range is 95% to 100% 
        // spo2 values like "dead" currently crash the deserializer. custom Jackson later?  
        if (spo2 == null) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "spo2";
            issue.message = "Missing SpO₂ value";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue;
        }
        if (spo2 < 95 || spo2 > 100) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "spo2";
            issue.message = "SpO₂ out of range (95-100%)";
            issue.severity = ValidationIssue.Severity.WARNING;
            return issue;
        }
        return null; 
    }
    private ValidationIssue validateTemperature(Double temperature, String patientId){
        // should be 35 - 38°C 
        if (temperature == null) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "temperature";
            issue.message = "Missing temperature value";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue;
        }
        if (temperature < 35|| temperature > 42) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "temperature";
            issue.message = "Temperature out of range (35-42°C)";
            issue.severity = ValidationIssue.Severity.WARNING;
            return issue;
        }
        // no issues found, return null: 
        return null; 
    }

    private ValidationIssue validateHeartRate(Integer heartRate, String patientId){
        if (heartRate == null){
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "heartRate";
            issue.message = "Missing heart rate";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue;
        }
        if (heartRate < 40 || heartRate > 180){
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "heartRate";
            issue.message = "Heart rate out of range (40 - 180 bpm)";
            issue.severity = ValidationIssue.Severity.WARNING;
            return issue;
        }
        return null; 
    }
    private ValidationIssue validateWeights(List<Double> weights, String patientId){
        if (weights == null) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "weights";
            issue.message = "Missing weight value";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue;
        }
        int wIssues = 0; 
        for (Double weight : weights) {
            if (weight == null){
                wIssues++; 
            }
            if (weight < 0 || weight > 600){
                wIssues++; 
            }
        }
        if (wIssues > 0){
                ValidationIssue issue = new ValidationIssue(); 
                issue.patientId = patientId; 
                issue.field = "weights";
                issue.message = "Invalid weight(s): " + wIssues + " issue(s) found.";
                issue.severity = ValidationIssue.Severity.ERROR;
                return issue;
        } 
        return null; 
    }
    private ValidationIssue validateDateTimeTaken(String dateTimeTaken, String patientId){
        if (dateTimeTaken == null || dateTimeTaken.isBlank()){
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "dateTimeTaken";
            issue.message = "Missing dateTimeTaken";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue;
        } 
        try {
            // parse ISO_LOCAL_DATE_TIME
            LocalDateTime localDate = LocalDateTime.parse(dateTimeTaken); 

            if (localDate.isAfter(LocalDateTime.now())) {
                ValidationIssue issue = new ValidationIssue(); 
                issue.patientId = patientId; 
                issue.field = "dateTimeTaken";
                issue.message = "Date cannot be in the future: " + localDate.toString();
                issue.severity = ValidationIssue.Severity.ERROR;
                return issue; 
            }
            if (localDate.isBefore(LocalDateTime.parse("1990-01-01T00:00:00"))){
                ValidationIssue issue = new ValidationIssue(); 
                issue.patientId = patientId; 
                issue.field = "dateTimeTaken";
                issue.message = "Very old dateTimeTaken: " + localDate.toString();
                issue.severity = ValidationIssue.Severity.WARNING;
                return issue; 
            }
        } catch (Exception e) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "dateTimeTaken";
            issue.message = "Invalid dateTimeTaken format. Expected yyyy-MM-dd or yyyy-MM-ddTHH:mm:ss";
            issue.severity = ValidationIssue.Severity.ERROR;
            return issue; 
        }
        return null; 
    }
    private ValidationIssue validateUserId(String userId, String patientId){
        if (userId == null || userId.isBlank()) {
            ValidationIssue issue =  new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "userId";
            issue.message = "Missing userId/source identifier";
            issue.severity = ValidationIssue.Severity.INFO;
            return issue; 
        }
        return null;
    }
}

