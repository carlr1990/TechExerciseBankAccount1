package tech.excercise.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SubmissionPrintException extends RuntimeException {
    public SubmissionPrintException(String message, JsonProcessingException e) {
        super(message);
    }
}
