package com.bichofull.backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "draws")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstPrize;
    private String secondPrize;
    private String thirdPrize;
    private String fourthPrize;
    private String fifthPrize;

    private LocalDateTime drawDate;

    @OneToMany(mappedBy = "draw")
    private List<Bet> bets;
}
