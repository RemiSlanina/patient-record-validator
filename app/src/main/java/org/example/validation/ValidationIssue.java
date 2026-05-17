package org.example.validation;

public class ValidationIssue {
    public String patientId; 
    public String field; 
    public String message; 
    public Severity severity; 
    public enum Severity {
        INFO,     // f.e. empty weights ? 
        WARNING, // f.e. missing timestamp ?  
        ERROR   // f.e. malformed temperature ?
    }
    public ValidationIssue(String patientId, String field, 
                String message, Severity severity){
        this.patientId = patientId; 
        this.field = field; 
        this.message = message; 
        this.severity = severity; 
    }

    @Override 
    public String toString() {
        return "patientId: " + patientId + 
        ", field: " + field +  
        ", message: " + message + 
        ", severity: " + severity + "."; 
    }
}
