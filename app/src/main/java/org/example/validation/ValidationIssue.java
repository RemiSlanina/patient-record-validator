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
}
