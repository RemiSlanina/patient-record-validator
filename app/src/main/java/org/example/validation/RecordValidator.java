package org.example.validation;

import org.example.model.PatientRecord;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordValidator {

    /**
     * 
     * Validates a raw PatientRecord using selected normalized values
     * from the cleaned record for reporting purposes.
     *
     * Validation messages currently display the cleaned patient ID
     * when available.
     *
     * At most one ValidationIssue per field is returned.
     */
    public List<ValidationIssue> validate(PatientRecord raw, PatientRecord cleaned) {
        List<ValidationIssue> issues = new ArrayList<>();
        // Validate all fields
        addIssueIfPresent(issues, validatePatientId(raw.patientId, cleaned.patientId)); 
        addIssueIfPresent(issues, validateSpo2(raw.spo2, cleaned.patientId)); 
        addIssueIfPresent(issues, validateTemperature(raw.temperature, cleaned.patientId));
        addIssueIfPresent(issues, validateHeartRate(raw.heartRate, cleaned.patientId));
        addIssueIfPresent(issues, validateWeights(raw.weights, cleaned.patientId));
        addIssueIfPresent(issues, validateDateTimeTaken(raw.dateTimeTaken, cleaned.patientId));
        addIssueIfPresent(issues, validateUserId(raw.userId, raw.patientId, cleaned.patientId));
        return issues;
    }

    // Helper functions
    private void addIssueIfPresent(List<ValidationIssue> issues, ValidationIssue issue){
        if (issue != null) {
            issues.add(issue); 
        }
    }

    private ValidationIssue validatePatientId(String patientId, String cleanedPatientId) {
        if (patientId == null || patientId.isBlank()) {
            return new ValidationIssue(
                "missing ID",
                "patientId",
                "Missing patient ID",
                ValidationIssue.Severity.ERROR
            );
        }
        if (!patientId.matches("^P-\\d+$")) {
            return new ValidationIssue(
                cleanedPatientId,
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
                "Empty weight values",
                ValidationIssue.Severity.INFO
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
                "Empty dateTimeTaken",
                ValidationIssue.Severity.WARNING
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

    private ValidationIssue validateUserId(String userId, String rawPatientId, String cleanedPatientId) {
        if (userId == null || userId.isBlank()) {
            return new ValidationIssue(
                cleanedPatientId,
                "userId",
                "Missing userId/source identifier",
                ValidationIssue.Severity.INFO
            );
        }
        if (userId == rawPatientId) {
            return new ValidationIssue(
                cleanedPatientId,
                "userId",
                "userId matches patientId",
                ValidationIssue.Severity.WARNING
            );
        }
        return null;
    }
}