package tech.excercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.excercise.entity.Transaction;
import tech.excercise.exception.TransactionQueueException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final AtomicReference<BigDecimal> balance = new AtomicReference<>(BigDecimal.ZERO);
    private final BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

    @Autowired
    private AuditSystemService auditService;

    @Override
    public void processTransaction(Transaction transaction) {
        balance.updateAndGet(currentBalance -> currentBalance.add(transaction.amount()));
        try {
            transactionQueue.put(transaction);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TransactionQueueException("Thread was Interrupted: ", e);
        }
    }

    @Override
    public double retrieveBalance() {
        return balance.get().doubleValue();
    }

    @Scheduled(fixedRate = 1000)
    public void processAuditBatch() {
        List<Transaction> transactionsToProcess = new ArrayList<>();
        transactionQueue.drainTo(transactionsToProcess);

        if (!transactionsToProcess.isEmpty()) {
            auditService.processBatch(transactionsToProcess);
        }
    }

}
