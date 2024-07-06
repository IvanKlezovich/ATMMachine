package org.example.service;

import lombok.Getter;
import org.example.entity.ATMMachine;
import org.example.entity.Card;

import java.util.Map;
/**
 * The {@code ATMService} class provides services for managing an ATM machine, including loading and saving card data,
 * and interacting with the ATM machine itself.
 */
public class ATMService {
    /**
     * The ATM machine managed by this service.
     */
    @Getter
    private final ATMMachine atm;

    /**
     * The file handler used for loading and saving card data.
     */
    private final FileHandler fileHandler;

    /**
     * Constructs a new {@code ATMService} with the specified file path, cash limit, and money buffer.
     *
     * @param filePath    the path to the file containing card data
     * @param cashLimit   the cash limit of the ATM machine
     * @param moneyBuffer the money buffer of the ATM machine
     */
    public ATMService(String filePath, Long cashLimit, Long moneyBuffer) {
        this.fileHandler = new FileHandler(filePath);
        Map<String, Card> cards = fileHandler.loadCards();
        this.atm = new ATMMachine(cashLimit, moneyBuffer);
        for (Card card : cards.values()) {
            atm.addCard(card);
        }
    }

    /**
     * Creates a new {@code FileHandler} instance with the specified file path.
     *
     * @param filePath the path to the file containing card data
     * @return a new {@code FileHandler} instance
     */
    protected FileHandler createFileHandler(String filePath) {
        return new FileHandler(filePath);
    }

    /**
     * Creates a new {@code ATMMachine} instance with the specified cash limit and money buffer.
     *
     * @param cashLimit   the cash limit of the ATM machine
     * @param moneyBuffer the money buffer of the ATM machine
     * @return a new {@code ATMMachine} instance
     */
    protected ATMMachine createATMMachine(Long cashLimit, Long moneyBuffer) {
        return new ATMMachine(cashLimit, moneyBuffer);
    }

    /**
     * Saves the current state of the ATM machine, including all card data, to the file.
     */
    public void saveState() {
        fileHandler.saveCards(atm.getCards());
    }
}
