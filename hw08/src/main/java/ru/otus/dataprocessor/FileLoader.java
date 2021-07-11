package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class FileLoader implements Loader {

    private final File file;

    public FileLoader(String fileName) {
        var url = getClass().getClassLoader().getResource(fileName);
        if (url != null) {
            file = new File(url.getFile());
        } else {
            throw new FileProcessingException("Invalid path.");
        }
    }

    @Override
    public List<Measurement> load() {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Measurement[] measurements = gson.fromJson(reader, Measurement[].class);
            return Arrays.asList(measurements);
        } catch (IOException e) {
            System.out.println("===> FILE:\n" + file.getAbsolutePath());
            throw new FileProcessingException("Can't read this file.");
        }
    }
}
