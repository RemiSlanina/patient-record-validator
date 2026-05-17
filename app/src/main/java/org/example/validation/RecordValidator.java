package org.example.validation;

import org.example.model.PatientRecord;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordValidator {

    public List<ValidationIssue> validate(PatientRecord record) {
        List<ValidationIssue> issues = new ArrayList<>();
        // Validate all fields
        addIssueIfPrsent(issues, validatePatientId(record.patientId, record.patientId)); 
        addIssueIfPrsent(issues, validateSpo2(record.spo2, record.patientId)); 
        addIssueIfPrsent(issues, validateTemperature(record.temperature, record.patientId));
        addIssueIfPrsent(issues, validateHeartRate(record.heartRate, record.patientId));
        addIssueIfPrsent(issues, validateWeights(record.weights, record.patientId));
        addIssueIfPrsent(issues, validateDateTimeTaken(record.dateTimeTaken, record.patientId));
        addIssueIfPrsent(issues, validateUserId(record.userId, record.patientId));
        return issues;
    }

    // Helper functions
    private void addIssueIfPrsent(List<ValidationIssue> issues, ValidationIssue issue){
        if (issue != null) {
            issues.add(issue); 
        }
    }

    private ValidationIssue validatePatientId(String patientId, String recordPatientId) {
        if (patientId == null || patientId.isBlank()) {
            return new ValidationIssue(
                recordPatientId,
                "patientId",
                "Missing patient ID",
                ValidationIssue.Severity.ERROR
            );
        }
        if (!patientId.matches("^P-\\d+$")) {
            return new ValidationIssue(
                recordPatientId,
                "patientId",
                "Invalid patient ID format",
                ValidationIssue.Severity.WARNING
            );
        }
        return null;
    }

    private ValidationIssue validateSpo2(Integer spo2, String patientId) {
        if (spo2 == null) {
            return new ValidationIssue(
                patientId,
                "spo2",
                "Missing SpO₂ value",
                ValidationIssue.Severity.ERROR
            );
        }
        if (spo2 < 95 || spo2 > 100) {
            return new ValidationIssue(
                patientId,
                "spo2",
                "SpO₂ out of range (95-100%)",
                ValidationIssue.Severity.WARNING
            );
        }
        return null;
    }

    private ValidationIssue validateTemperature(Double temperature, String patientId) {
        if (temperature == null) {
            return new ValidationIssue(
                patientId,
                "temperature",
                "Missing temperature value",
                ValidationIssue.Severity.ERROR
            );
        }
        if (temperature < 35 || temperature > 42) {
            return new ValidationIssue(
                patientId,
                "temperature",
                "Temperature out of range (35-42°C)",
                ValidationIssue.Severity.WARNING
            );
        }
        return null;
    }

    private ValidationIssue validateHeartRate(Integer heartRate, String patientId) {
        if (heartRate == null) {
            return new ValidationIssue(
                patientId,
                "heartRate",
                "Missing heart rate",
                ValidationIssue.Severity.ERROR
            );
        }
        if (heartRate < 40 || heartRate > 180) {
            return new ValidationIssue(
                patientId,
                "heartRate",
                "Heart rate out of range (40-180 bpm)",
                ValidationIssue.Severity.WARNING
            );
        }
        return null;
    }

    private ValidationIssue validateWeights(List<Double> weights, String patientId) {
        if (weights == null) {
            return new ValidationIssue(
                patientId,
                "weights",
                "Missing weight values",
                ValidationIssue.Severity.ERROR
            );
        }

        int invalidWeights = 0;
        for (Double weight : weights) {
            if (weight == null || weight < 0 || weight > 600) {
                invalidWeights++;
            }
        }

        if (invalidWeights > 0) {
            return new ValidationIssue(
                patientId,
                "weights",
                "Invalid weight(s): " + invalidWeights + " issue(s) found",
                ValidationIssue.Severity.ERROR
            );
        }
        return null;
    }

    private ValidationIssue validateDateTimeTaken(String dateTimeTaken, String patientId) {
        if (dateTimeTaken == null || dateTimeTaken.isBlank()) {
            return new ValidationIssue(
                patientId,
                "dateTimeTaken",
                "Missing dateTimeTaken",
                ValidationIssue.Severity.ERROR
            );
        }

        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeTaken);

            if (dateTime.isAfter(LocalDateTime.now())) {
                return new ValidationIssue(
                    patientId,
                    "dateTimeTaken",
                    "Date cannot be in the future: " + dateTime,
                    ValidationIssue.Severity.ERROR
                );
            }

            if (dateTime.isBefore(LocalDateTime.parse("1990-01-01T00:00:00"))) {
                return new ValidationIssue(
                    patientId,
                    "dateTimeTaken",
                    "Very old dateTimeTaken: " + dateTime,
                    ValidationIssue.Severity.WARNING
                );
            }
        } catch (Exception e) {
            return new ValidationIssue(
                patientId,
                "dateTimeTaken",
                "Invalid dateTimeTaken format. Expected yyyy-MM-ddTHH:mm:ss",
                ValidationIssue.Severity.ERROR
            );
        }
        return null;
    }

    private ValidationIssue validateUserId(String userId, String patientId) {
        if (userId == null || userId.isBlank()) {
            return new ValidationIssue(
                patientId,
                "userId",
                "Missing userId/source identifier",
                ValidationIssue.Severity.INFO
            );
        }
        return null;
    }
}