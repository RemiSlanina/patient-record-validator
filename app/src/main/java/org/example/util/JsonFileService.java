package org.example.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.example.model.PatientRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for loading and saving JSON files containing PatientRecord data.
 * Files are loaded from the classpath (e.g., src/main/resources/).
 */
public class JsonFileService {
    private final ObjectMapper mapper; 

    public JsonFileService() {
        this.mapper = new ObjectMapper(); 
    }

    /**
     * 
     * Loads a list of PatientRecord objects from app/src/main/resources/.
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
     * @param resourcePath Path to the JSON file in the classpath (e.g., "sample-data/patients-1.json").
     * @return List of PatientRecord objects parsed from the JSON file.
     * @throws IOException If the resource is not found or cannot be read.
     */
    public List<PatientRecord> loadRecords(String resourcePath) throws IOException {
        String json = readResourceAsString(resourcePath); 
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
            List.class, 
            PatientRecord.class)); 
    }

/**
     * Reads a resource file from the classpath as a String.
     *
     * @param resourcePath Path to the resource (e.g., "sample-data/patients-1.json").
     *                     A leading "/" is optional and will be added if missing.
     * @return The content of the resource file as a String.
     * @throws IOException If the resource is not found.
     */
    public String readResourceAsString(String resourcePath) throws IOException {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath; 
        }
        InputStream inputStream = JsonFileService.class.getResourceAsStream(resourcePath); 
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return new String(inputStream.readAllBytes());
    }


    /**
     * Writes a list of cleaned PatientRecord data to a JSON file.
     * Output files are written separately to the project root.
     * 
     * @param records    List of PatientRecord objects to save.
     * @param outputPath Path to the output file (e.g., "output/cleaned-patients.json").
     * @throws IOException If the file cannot be written.
     */
    public void saveRecords(List<PatientRecord> records, String outputPath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputPath), records);
    }

}
