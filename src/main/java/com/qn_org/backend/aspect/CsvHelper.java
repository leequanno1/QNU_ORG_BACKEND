package com.qn_org.backend.aspect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;

public class CsvHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonToCsv(String jsonString, String CSV_FILE_PATH) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            // Write header if file is empty
            if (new java.io.File(CSV_FILE_PATH).length() == 0) {
                StringBuilder headerBuilder = new StringBuilder();
                jsonNode.fieldNames().forEachRemaining(fieldName -> {
                    if (!headerBuilder.isEmpty()) headerBuilder.append(",");
                    headerBuilder.append(fieldName);
                });
                writer.append(headerBuilder.toString());
                writer.append("\n");
            }
            for (JsonNode value : jsonNode) {
                writer.append(value.asText());
                writer.append(",");
            }
            writer.append("\n");
        }
    }
}
