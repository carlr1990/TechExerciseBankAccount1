package tech.excercise.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record Transaction(String id, BigDecimal amount) {
    public Transaction(BigDecimal amount) {
        this(UUID.randomUUID().toString(), amount);
    }
}