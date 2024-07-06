package org.example.entity;

import lombok.*;

/**
 * The {@code Card} class represents a bank card with various attributes such as card number,
 * PIN, balance, blocked status, and block time.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    /**
     * The unique number of the card.
     */
    private String numberCard;

    /**
     * The Personal Identification Number (PIN) associated with the card.
     */
    private short pin;

    /**
     * The current balance of the card.
     */
    @Setter
    private long balance;

    /**
     * Indicates whether the card is currently blocked.
     */
    @Setter
    private boolean isBlocked;

    /**
     * The time at which the card was blocked, in milliseconds since the epoch.
     */
    @Setter
    private long blockTime;
}
