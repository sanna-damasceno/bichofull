package com.bichofull.backend.dto;

import java.math.BigDecimal;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;

    public UserResponseDTO(Long id, String name, String email, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getBalance() { return balance; }
}