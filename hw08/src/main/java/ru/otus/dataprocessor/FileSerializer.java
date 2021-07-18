package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final File file;

    public FileSerializer(String fileName) {
        file = new File(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new Gson();
            writer.write(gson.toJson(data));
        } catch (IOException e) {
            throw new FileProcessingException("Can't write to this file.");
        }
    }
}
