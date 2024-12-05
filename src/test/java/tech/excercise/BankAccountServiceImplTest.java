package tech.excercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.excercise.entity.Transaction;
import tech.excercise.service.AuditSystemService;
import tech.excercise.service.BankAccountServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BankAccountServiceImplTest {

    @Autowired
    AuditSystemService auditService;

    @Test
    public void testAccountBalance() {
        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl();
        bankAccountService.processTransaction(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(500)));
        bankAccountService.processTransaction(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(-300)));
        bankAccountService.processTransaction(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(500)));

        assertEquals(700.0, bankAccountService.retrieveBalance());
    }

    @Test
    public void testBatchAbsoluteAmount() {

        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(100)));
        list.add(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(-200)));
        list.add(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(50)));
        list.add(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(70)));
        list.add(new Transaction(String.valueOf(Math.random()), BigDecimal.valueOf(-20)));

        auditService.processBatch(list);
        double batchAbsoluteValue = auditService.getCurrentBatchCopy().getTotalValueOfAllTransactions().doubleValue();

        assertEquals(440.0, batchAbsoluteValue);
    }
}