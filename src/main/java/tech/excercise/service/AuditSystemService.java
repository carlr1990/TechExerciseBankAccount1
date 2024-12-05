package tech.excercise.service;

import tech.excercise.entity.AuditBatch;
import tech.excercise.entity.Transaction;

import java.util.List;

public interface AuditSystemService {
    void processBatch(List<Transaction> transactions);
    void submitToAuditSystem(List<AuditBatch> submission);
    void printSubmission(List<AuditBatch> submission);
    AuditBatch getCurrentBatchCopy();

}
