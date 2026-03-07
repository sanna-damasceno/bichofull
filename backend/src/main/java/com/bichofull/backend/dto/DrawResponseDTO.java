package com.bichofull.backend.dto;

import java.time.LocalDateTime;

public class DrawResponseDTO {

    private Long id;
    private LocalDateTime drawDate;

    private String firstPrize;
    private String secondPrize;
    private String thirdPrize;
    private String fourthPrize;
    private String fifthPrize;

    public DrawResponseDTO(
            Long id,
            LocalDateTime drawDate,
            String firstPrize,
            String secondPrize,
            String thirdPrize,
            String fourthPrize,
            String fifthPrize
    ) {
        this.id = id;
        this.drawDate = drawDate;
        this.firstPrize = firstPrize;
        this.secondPrize = secondPrize;
        this.thirdPrize = thirdPrize;
        this.fourthPrize = fourthPrize;
        this.fifthPrize = fifthPrize;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDrawDate() {
        return drawDate;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public String getThirdPrize() {
        return thirdPrize;
    }

    public String getFourthPrize() {
        return fourthPrize;
    }

    public String getFifthPrize() {
        return fifthPrize;
    }
}