package tech.excercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.excercise.entity.Transaction;
import tech.excercise.service.BankAccountService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
public class TransactionProducer {

    private final Random random = new Random();

    @Value("${transactionGen.minAmountRange}")
    private double MIN_RANGE;
    @Value("${transactionGen.maxAmountRange}")
    private double MAX_RANGE;

    @Autowired
    private BankAccountService bankAccountService;

    @Scheduled(fixedRateString = "${transactionGen.produceCreditRateInMilliSec}")
    public void produceCredits() {
        Transaction credit =  generateTransaction(true);
        bankAccountService.processTransaction(credit);
    }

    @Scheduled(fixedRateString = "${transactionGen.produceDebitRateInMilliSec}")
    public void produceDebits() {
        Transaction debit =  generateTransaction(false);
        bankAccountService.processTransaction(debit);
    }

    private Transaction generateTransaction(boolean isCredit) {
        double randomNumber = MIN_RANGE + (MAX_RANGE - MIN_RANGE) * random.nextDouble();
        BigDecimal amount = new BigDecimal(randomNumber).setScale(2, RoundingMode.HALF_UP);
        return new Transaction(isCredit ? amount : amount.negate());
    }

}
