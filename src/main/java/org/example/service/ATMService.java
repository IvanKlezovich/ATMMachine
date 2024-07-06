package org.example.service;

import lombok.Getter;
import org.example.entity.ATMMachine;
import org.example.entity.Card;

import java.util.Map;

public class ATMService {
    @Getter
    private final ATMMachine atm;
    private final FileHandler fileHandler;

    public ATMService(String filePath, Long cashLimit, Long moneyBuffer) {
        this.fileHandler = new FileHandler(filePath);
        Map<String, Card> cards = fileHandler.loadCards();
        this.atm = new ATMMachine(cashLimit, moneyBuffer);
        for (Card card : cards.values()) {
            atm.addCard(card);
        }
    }

    public void saveState() {
        fileHandler.saveCards(atm.getCards());
    }

}
