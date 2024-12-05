package tech.excercise.exception;


public class TransactionQueueException extends RuntimeException {
    public TransactionQueueException(String message, InterruptedException e) {
        super(message);
    }
}
