package org.example.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ATMMachine {
    private long moneyBuffer;
    private final long cashLimit;
    private Map<String, Card> cards;

    public ATMMachine(long cashLimit, long moneyBuffer) {
        this.cashLimit = cashLimit;
        this.moneyBuffer = moneyBuffer;
        cards = new HashMap<>();
    }

    public boolean validateCardNumber(String cardNumber){
        return cards.containsKey(cardNumber);
    }

    public boolean validatePin(String cardNumber, long pin){
        Card card = cards.get(cardNumber);
        return card != null && card.getPin() == pin;
    }

    public long checkBalance(String cardNumber){
        return cards.get(cardNumber).getBalance();
    }

    public String withdraw(String cardNumber, long amount){
        Card card = cards.get(cardNumber);
        if(amount > moneyBuffer){
            return "Недостаточно средств в банкомате";
        }
        if(amount > card.getBalance()){
            return "Недостаточно средств на счете";
        }
        card.setBalance(card.getBalance() - amount);
        moneyBuffer -= amount;
        return String.format("Успешно снято %d единиц. Новый баланс: %d",
                amount, card.getBalance());
    }

    public String deposit(String cardNUmber, long amount){
        if(amount > 1_000_000){
            return "Сумма пополнения не должна превышать 1 000 000";
        }
        Card card = cards.get(cardNUmber);
        card.setBalance(card.getBalance() + amount);
        moneyBuffer += amount;
        return String.format("Успешно пополнено на %d единиц. Новый баланс: %d",
                amount, card.getBalance());
    }

    public void addCard(Card card){
        cards.put(String.valueOf(card.getNumberCard()), card);
    }

    public Card getCard(String cardNumber){
        return cards.get(cardNumber);
    }

    public void blockCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        card.setBlocked(true);
        card.setBlockTime(System.currentTimeMillis());

    }

    public void unblockCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (System.currentTimeMillis() - card.getBlockTime() > 24 * 60 * 60 * 1000) {
            card.setBlocked(false);
        }
    }
}
