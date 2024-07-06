package org.example.atmmachine.service;

import org.example.entity.Card;
import org.example.service.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code FileHandlerTest} class contains unit tests for the {@code FileHandler} class.
 */
class FileHandlerTest {

    @TempDir
    Path tempDir;

    private FileHandler fileHandler;
    private Path tempFilePath;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        tempFilePath = tempDir.resolve("testCards.txt");
        fileHandler = new FileHandler(tempFilePath.toString());
    }

    /**
     * Tests the {@code loadCards} method of the {@code FileHandler} class.
     * @throws IOException if an I/O error occurs
     */
    @Test
    void testLoadCards() throws IOException {
        String content = "1234-5678-9012-3456 1234 1000 false 0\n" +
                         "9876-5432-1098-7654 4321 500 true 1234567890";
        Files.write(tempFilePath, content.getBytes());

        Map<String, Card> cards = fileHandler.loadCards();

        assertEquals(2, cards.size());
        assertTrue(cards.containsKey("1234-5678-9012-3456"));
        assertTrue(cards.containsKey("9876-5432-1098-7654"));

        Card card1 = cards.get("1234-5678-9012-3456");
        assertEquals("1234-5678-9012-3456", card1.getNumberCard());
        assertEquals(1234, card1.getPin());
        assertEquals(1000, card1.getBalance());
        assertFalse(card1.isBlocked());
        assertEquals(0, card1.getBlockTime());

        Card card2 = cards.get("9876-5432-1098-7654");
        assertEquals("9876-5432-1098-7654", card2.getNumberCard());
        assertEquals(4321, card2.getPin());
        assertEquals(500, card2.getBalance());
        assertTrue(card2.isBlocked());
        assertEquals(1234567890, card2.getBlockTime());
    }

    /**
     * Tests the {@code saveCards} method of the {@code FileHandler} class.
     * @throws IOException if an I/O error occurs
     */
    @Test
    void testSaveCards() throws IOException {
        Map<String, Card> cards = new HashMap<>();
        cards.put("1234-5678-9012-3456", new Card("1234-5678-9012-3456", (short) 1234, 1000L, false, 0L));
        cards.put("9876-5432-1098-7654", new Card("9876-5432-1098-7654", (short) 4321, 500L, true, 1234567890L));

        fileHandler.saveCards(cards);

        String content = new String(Files.readAllBytes(tempFilePath));
        String expectedContent = "1234-5678-9012-3456 1234 1000 false 0\n" +
                                 "9876-5432-1098-7654 4321 500 true 1234567890\n";
        assertEquals(expectedContent, content);
    }
}
