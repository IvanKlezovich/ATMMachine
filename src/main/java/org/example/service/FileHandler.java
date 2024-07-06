package org.example.service;

import org.example.entity.Card;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Card> loadCards() {
        Map<String, Card> cards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 5) {
                    cards.put(parts[0], new Card(parts[0], Short.parseShort(parts[1]), Long.parseLong(parts[2]), Boolean.parseBoolean(parts[3]), Long.parseLong(parts[4])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public void saveCards(Map<String, Card> cards) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Card card : cards.values()) {
                pw.println(card.getNumberCard() + " " + card.getPin() + " " + card.getBalance() + " " + card.isBlocked() + " " + card.getBlockTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
