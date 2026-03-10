package com.bichofull.backend.dto;

import java.math.BigDecimal;

public class UserBalanceResponseDTO {

    private BigDecimal balance;

    public UserBalanceResponseDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}