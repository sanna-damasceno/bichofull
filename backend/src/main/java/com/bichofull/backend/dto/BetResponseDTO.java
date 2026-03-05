package com.bichofull.backend.dto;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetResponseDTO {

    private Long id;
    private BetType type;
    private String chosenNumber;
    private BigDecimal amount;
    private BetStatus status;
    private BigDecimal prize;
    private LocalDateTime createdAt;

    public BetResponseDTO(Long id, BetType type, String chosenNumber,
                          BigDecimal amount, BetStatus status,
                          BigDecimal prize, LocalDateTime createdAt) {

        this.id = id;
        this.type = type;
        this.chosenNumber = chosenNumber;
        this.amount = amount;
        this.status = status;
        this.prize = prize;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public BetType getType() { return type; }
    public String getChosenNumber() { return chosenNumber; }
    public BigDecimal getAmount() { return amount; }
    public BetStatus getStatus() { return status; }
    public BigDecimal getPrize() { return prize; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}