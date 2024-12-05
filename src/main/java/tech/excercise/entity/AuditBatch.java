package tech.excercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AuditBatch {

    @JsonIgnore
    private List<Transaction> transactionList = new ArrayList<>();
    private BigDecimal totalValueOfAllTransactions = BigDecimal.ZERO;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public BigDecimal getTotalValueOfAllTransactions() {
        return totalValueOfAllTransactions;
    }

    public void setTotalValueOfAllTransactions(BigDecimal totalValueOfAllTransactions) {
        this.totalValueOfAllTransactions = totalValueOfAllTransactions;
    }

    public int getCountOfTransactions() {
        return transactionList.size();
    }

}
