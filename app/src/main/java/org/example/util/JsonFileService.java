package org.example.util;

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

}
