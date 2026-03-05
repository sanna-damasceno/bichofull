package com.bichofull.backend.model;


import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BetType type;

    private String chosenNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BetStatus status;

    private BigDecimal prize;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "draw_id")
    private Draw draw;
}