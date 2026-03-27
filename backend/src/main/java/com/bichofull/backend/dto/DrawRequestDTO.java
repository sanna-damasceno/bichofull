package com.bichofull.backend.dto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

public class DrawRequestDTO {

    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "Must be 4 digits")
    private String firstPrize;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String secondPrize;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String thirdPrize;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String fourthPrize;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String fifthPrize;

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        this.secondPrize = secondPrize;
    }

    public String getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(String thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public String getFourthPrize() {
        return fourthPrize;
    }

    public void setFourthPrize(String fourthPrize) {
        this.fourthPrize = fourthPrize;
    }

    public String getFifthPrize() {
        return fifthPrize;
    }

    public void setFifthPrize(String fifthPrize) {
        this.fifthPrize = fifthPrize;
    }
}