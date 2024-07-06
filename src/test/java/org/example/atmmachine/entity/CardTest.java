package org.example.atmmachine.entity;

import org.example.entity.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code CardTest} class contains unit tests for the {@code Card} class.
 */
class CardTest {
    private Card card;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        card = new Card("1234-5678-9012-3456", (short) 1234, 1000L, false, 0L);
    }

    /**
     * Tests the constructor and getter methods of the {@code Card} class.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("1234-5678-9012-3456", card.getNumberCard());
        assertEquals(1234, card.getPin());
        assertEquals(1000L, card.getBalance());
        assertFalse(card.isBlocked());
        assertEquals(0L, card.getBlockTime());
    }

    /**
     * Tests the {@code setBalance} method of the {@code Card} class.
     */
    @Test
    void testSetBalance() {
        card.setBalance(2000L);
        assertEquals(2000L, card.getBalance());
    }

    /**
     * Tests the {@code setBlocked} method of the {@code Card} class.
     */
    @Test
    void testSetBlocked() {
        card.setBlocked(true);
        assertTrue(card.isBlocked());
    }

    /**
     * Tests the {@code setBlockTime} method of the {@code Card} class.
     */
    @Test
    void testSetBlockTime() {
        card.setBlockTime(System.currentTimeMillis());
        assertTrue(card.getBlockTime() > 0L);
    }

    /**
     * Tests the no-argument constructor of the {@code Card} class.
     */
    @Test
    void testNoArgsConstructor() {
        Card noArgsCard = new Card();
        assertNull(noArgsCard.getNumberCard());
        assertEquals(0, noArgsCard.getPin());
        assertEquals(0L, noArgsCard.getBalance());
        assertFalse(noArgsCard.isBlocked());
        assertEquals(0L, noArgsCard.getBlockTime());
    }
}
