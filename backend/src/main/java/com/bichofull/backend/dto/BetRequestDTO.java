package com.bichofull.backend.dto;

import com.bichofull.backend.enums.BetType;
import java.math.BigDecimal;

public class BetRequestDTO {

    private BetType type;
    private String chosenNumber;
    private BigDecimal amount;

    public BetType getType() {
        return type;
    }

    public void setType(BetType type) {
        this.type = type;
    }

    public String getChosenNumber() {
        return chosenNumber;
    }

    public void setChosenNumber(String chosenNumber) {
        this.chosenNumber = chosenNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}