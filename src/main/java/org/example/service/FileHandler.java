package org.example.service;

import org.example.entity.Card;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code FileHandler} class is responsible for reading and writing card data to and from a file.
 */
public class FileHandler {
    /**
     * The path to the file containing card data.
     */
    private String filePath;

    /**
     * Constructs a new {@code FileHandler} with the specified file path.
     *
     * @param filePath the path to the file containing card data
     */
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads card data from the file into a map.
     *
     * @return a map containing card data, where the key is the card number and the value is the {@code Card} object
     */
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

    /**
     * Saves card data from a map to the file.
     *
     * @param cards a map containing card data, where the key is the card number and the value is the {@code Card} object
     */
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
