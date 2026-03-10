package com.bichofull.backend.dto;

import com.bichofull.backend.enums.BetStatus;
import com.bichofull.backend.enums.BetType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetHistoryDTO {

    private Long id;
    private BetType type;
    private String chosenNumber;
    private BigDecimal amount;
    private BetStatus status;
    private BigDecimal prize;
    private LocalDateTime createdAt;
}