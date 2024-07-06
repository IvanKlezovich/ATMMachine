package org.example.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ATMMachine} class represents an ATM machine that can handle various operations
 * such as validating card numbers and PINs, checking balances, withdrawing and depositing funds,
 * and managing card blocking and unblocking.
 */
@Data
public class ATMMachine {
    /**
     * The amount of money currently available in the ATM machine.
     */
    private long moneyBuffer;

    /**
     * The maximum amount of cash that the ATM machine can hold.
     */
    private final long cashLimit;

    /**
     * A map containing all the cards registered with the ATM machine, keyed by card number.
     */
    private Map<String, Card> cards;

    /**
     * Constructs a new {@code ATMMachine} with the specified cash limit and initial money buffer.
     *
     * @param cashLimit   the maximum amount of cash that the ATM machine can hold
     * @param moneyBuffer the initial amount of money available in the ATM machine
     */
    public ATMMachine(long cashLimit, long moneyBuffer) {
        this.cashLimit = cashLimit;
        this.moneyBuffer = moneyBuffer;
        cards = new HashMap<>();
    }

    /**
     * Validates whether the provided card number exists in the ATM machine's card registry.
     *
     * @param cardNumber the card number to validate
     * @return {@code true} if the card number is valid, {@code false} otherwise
     */
    public boolean validateCardNumber(String cardNumber) {
        return cards.containsKey(cardNumber);
    }

    /**
     * Validates whether the provided PIN matches the PIN associated with the given card number.
     *
     * @param cardNumber the card number to validate the PIN against
     * @param pin        the PIN to validate
     * @return {@code true} if the PIN is valid, {@code false} otherwise
     */
    public boolean validatePin(String cardNumber, long pin) {
        Card card = cards.get(cardNumber);
        return card != null && card.getPin() == pin;
    }

    /**
     * Retrieves the current balance of the card associated with the given card number.
     *
     * @param cardNumber the card number to check the balance for
     * @return the current balance of the card
     */
    public long checkBalance(String cardNumber) {
        return cards.get(cardNumber).getBalance();
    }

    /**
     * Withdraws the specified amount from the card associated with the given card number.
     *
     * @param cardNumber the card number to withdraw funds from
     * @param amount     the amount to withdraw
     * @return a message indicating the result of the withdrawal operation
     */
    public String withdraw(String cardNumber, long amount) {
        Card card = cards.get(cardNumber);
        if (amount > moneyBuffer) {
            return "Недостаточно средств в банкомате";
        }
        if (amount > card.getBalance()) {
            return "Недостаточно средств на счете";
        }
        card.setBalance(card.getBalance() - amount);
        moneyBuffer -= amount;
        return String.format("Успешно снято %d единиц. Новый баланс: %d", amount, card.getBalance());
    }

    /**
     * Deposits the specified amount to the card associated with the given card number.
     *
     * @param cardNumber the card number to deposit funds to
     * @param amount     the amount to deposit
     * @return a message indicating the result of the deposit operation
     */
    public String deposit(String cardNumber, long amount) {
        if (amount > 1_000_000) {
            return "Сумма пополнения не должна превышать 1 000 000";
        }
        Card card = cards.get(cardNumber);
        card.setBalance(card.getBalance() + amount);
        moneyBuffer += amount;
        return String.format("Успешно пополнено на %d единиц. Новый баланс: %d", amount, card.getBalance());
    }

    /**
     * Adds a new card to the ATM machine's card registry.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.put(String.valueOf(card.getNumberCard()), card);
    }

    /**
     * Retrieves the card associated with the given card number.
     *
     * @param cardNumber the card number to retrieve
     * @return the card associated with the given card number
     */
    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    /**
     * Blocks the card associated with the given card number.
     *
     * @param cardNumber the card number to block
     */
    public void blockCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        card.setBlocked(true);
        card.setBlockTime(System.currentTimeMillis());
    }

    /**
     * Unblocks the card associated with the given card number if the block time has expired.
     *
     * @param cardNumber the card number to unblock
     */
    public void unblockCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (System.currentTimeMillis() - card.getBlockTime() > 24 * 60 * 60 * 1000) {
            card.setBlocked(false);
        }
    }
}
