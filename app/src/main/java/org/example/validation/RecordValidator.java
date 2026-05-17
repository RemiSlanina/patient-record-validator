package org.example.validation;
import org.example.model.PatientRecord;
import java.util.ArrayList;
import java.util.List;


public class RecordValidator {
    public List<ValidationIssue> validate(PatientRecord record) {
        List<ValidationIssue> issues = new ArrayList<>(); 
        // test patientId
        ValidationIssue issue = validatePatientId(record.patientId);
        if (issue != null) issues.add(issue);
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
             issue.severity = ValidationIssue.Severity.ERROR;
             return issue; 
        }
        if (!patientId.matches("^P-\\d+$")) {
            ValidationIssue issue = new ValidationIssue(); 
            issue.patientId = patientId; 
            issue.field = "patientId"; 
            issue.message = "Invalid patient ID format"; 
            issue.severity = ValidationIssue.Severity.WARNING; 
            return issue; 
        }
        // no issues: 
        return null; 
    }
}

