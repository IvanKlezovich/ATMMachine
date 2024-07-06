package org.example.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String numberCard;
    private short pin;
    @Setter
    private long balance;
    @Setter
    private boolean isBlocked;
    @Setter
    private long blockTime;
}
