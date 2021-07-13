package ru.otus.dataprocessor;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(Exception ex) {
        super(ex);
    }

    public FileProcessingException(String msg) {
        super(msg);
    }
}
