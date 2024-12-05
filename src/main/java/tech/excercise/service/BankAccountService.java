package tech.excercise.service;

import tech.excercise.entity.Transaction;

public interface BankAccountService {
    void processTransaction(Transaction transaction);
    double retrieveBalance();

}
