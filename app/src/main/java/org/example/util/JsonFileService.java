package org.example.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.example.model.PatientRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileService {
    private final ObjectMapper mapper; 

    public JsonFileService() {
        this.mapper = new ObjectMapper(); 
    }

    /**
     * 
     * Loads PatientRecord JSON files from app/src/main/resources/.
     *
     * Example resource paths:
     * - sample-data/patients-1.json
     * - sample-data/patients-2.json
     *
     * Default execution:
     * ./gradlew run
     *
     * Custom input:
     * ./gradlew run --args="sample-data/patients-2.json"
     */
    public List<PatientRecord> loadRecords(String resourcePath) throws IOException {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath; 
        }
        InputStream inputStream = JsonFileService.class.getResourceAsStream(resourcePath); 
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        String json = new String(inputStream.readAllBytes());
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
            List.class, 
            PatientRecord.class)); 
    }

    /**
     * Writes cleaned PatientRecord data to a JSON file.
     * Output files are written separately to the project root.
     */
    public void saveRecords(List<PatientRecord> records, String outputPath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputPath), records);
    }

}
