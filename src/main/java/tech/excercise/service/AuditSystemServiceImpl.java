package tech.excercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.excercise.entity.AuditBatch;
import tech.excercise.entity.SubmissionPrintWrapper;
import tech.excercise.entity.Transaction;
import tech.excercise.exception.SubmissionPrintException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditSystemServiceImpl implements AuditSystemService {
    private List<AuditBatch> submission;
    private AuditBatch batch;
    private BigDecimal totalValue = BigDecimal.ZERO;
    private int transactionCount;

    @Value("${audit.maxBatchAbsoluteAmount}")
    private double MAX_BATCH_VALUE;
    @Value("${audit.transactionsPerSubmit}")
    private int TRANSACTIONS_PER_SUBMIT;

    @Override
    public void processBatch(List<Transaction> transactions) {
        if (batch == null) {
            submission = new ArrayList<>();
            batch = new AuditBatch();
        }

        for (Transaction transaction : transactions) {
            totalValue = totalValue.add(transaction.amount().abs());
            transactionCount++;

            if(totalValue.doubleValue() > MAX_BATCH_VALUE) {
                submission.add(batch);
                //create new batch and reset Batch Total to Last Transaction
                batch = new AuditBatch();
                totalValue = transaction.amount().abs();
            }

            batch.getTransactionList().add(transaction);
            batch.setTotalValueOfAllTransactions(totalValue);

            if (transactionCount == TRANSACTIONS_PER_SUBMIT) {
                submission.add(batch);
                submitToAuditSystem(submission);

                batch = new AuditBatch();
                totalValue = BigDecimal.ZERO;
                transactionCount = 0;
                submission.clear();
            }
        }
    }

    @Override
    public void submitToAuditSystem(List<AuditBatch> submission) {
        // Call Service to send Submission to Audit System
        printSubmission(submission);
    }

    @Override
    public void printSubmission(List<AuditBatch> submission) {
        SubmissionPrintWrapper printWrapper = new SubmissionPrintWrapper(submission);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStyle;
        try {
            jsonStyle = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(printWrapper);
        } catch (JsonProcessingException e) {
            throw new SubmissionPrintException("Printing Submission - Json Error", e);
        }
        System.out.println(jsonStyle);
    }

    @Override
    public AuditBatch getCurrentBatchCopy() {
        AuditBatch copyBatch = new AuditBatch();
        copyBatch.setTotalValueOfAllTransactions(batch.getTotalValueOfAllTransactions());
        copyBatch.setTransactionList(new ArrayList<>(batch.getTransactionList()));
        return copyBatch;
    }

}
