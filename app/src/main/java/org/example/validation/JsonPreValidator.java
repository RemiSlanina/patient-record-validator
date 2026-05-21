package org.example.validation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPreValidator {
    private static final ObjectMapper mapper = new ObjectMapper(); 
    
    public static List<ValidationIssue> validateJsonTypes(String json) {
        List<ValidationIssue> issues = new ArrayList<>(); 
        try {
            JsonNode root = mapper.readTree(json); 
            for (JsonNode recordNode : root) {
                JsonNode spo2Node = recordNode.get("spo2"); 
                if (spo2Node != null && spo2Node.isTextual()) {
                    try {
                        Double.parseDouble((spo2Node.asText())); 
                    } catch (NumberFormatException e) {
                        // probably an issue 
                        issues.add(new ValidationIssue(
                            "spo2", 
                         "Expected a number, but got a string: '" + spo2Node.asText() + "'", 
                            ValidationIssue.Severity.ERROR
                        ));
                    }
                }
                
                JsonNode temperatureNode = recordNode.get("temperature"); 
                if (temperatureNode != null && temperatureNode.isTextual()){
                    try {
                        Double.parseDouble(temperatureNode.asText()); 
                    } catch (NumberFormatException e) {
                        // probably the same 
                        issues.add(new ValidationIssue(
                            "temperature", 
                         "Expected a number, but got a string: '" + temperatureNode.asText() + "'", 
                            ValidationIssue.Severity.ERROR
                        ));
                    }

                }
            }
        } catch (Exception e) {
            issues.add(new ValidationIssue(
                "JSON", 
                "Failed to parse JSON " + e.getMessage(), 
                ValidationIssue.Severity.ERROR));
        }
        // for (ValidationIssue validationIssue : issues) {
        //     System.err.println(validationIssue);
        // }
        return issues; 
    }
}
