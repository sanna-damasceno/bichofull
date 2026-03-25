package com.bichofull.backend.dto;

import java.math.BigDecimal;

import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.Bet;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;
    private BigDecimal totalWon;
    private BigDecimal totalLost;
    private BigDecimal totalPending;

    public UserResponseDTO(Long id, String name, String email, BigDecimal balance,
        BigDecimal totalWon, BigDecimal totalLost, BigDecimal totalPending
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.totalWon = totalWon;
        this.totalLost = totalLost;
        this.totalPending = totalPending;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getBalance() { return balance; }
    public BigDecimal getTotalWon() { return totalWon;}
    public BigDecimal getTotalLost() { return totalLost;}
    public BigDecimal getTotalPending() { return totalPending;}



}