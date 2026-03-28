package com.bichofull.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    private Integer id;

    private Integer groupNumber;

    private String name;

    private String dezenas;

}