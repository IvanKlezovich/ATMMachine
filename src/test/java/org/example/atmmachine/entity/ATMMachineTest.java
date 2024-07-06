package org.example.atmmachine.entity;

import org.example.entity.ATMMachine;
import org.example.entity.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code ATMMachineTest} class contains unit tests for the {@code ATMMachine} class.
 */
class ATMMachineTest {

    private ATMMachine atmMachine;
    private Card card;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        atmMachine = new ATMMachine(10000L, 5000L);
        card = new Card("1234-5678-9012-3456", (short) 1234, 1000L, false, 0L);
        atmMachine.addCard(card);
    }

    /**
     * Tests the {@code validateCardNumber} method of the {@code ATMMachine} class.
     */
    @Test
    void testValidateCardNumber() {
        assertTrue(atmMachine.validateCardNumber("1234-5678-9012-3456"));
        assertFalse(atmMachine.validateCardNumber("9999-9999-9999-9999"));
    }

    /**
     * Tests the {@code validatePin} method of the {@code ATMMachine} class.
     */
    @Test
    void testValidatePin() {
        assertTrue(atmMachine.validatePin("1234-5678-9012-3456", 1234));
        assertFalse(atmMachine.validatePin("1234-5678-9012-3456", 9999));
    }

    /**
     * Tests the {@code checkBalance} method of the {@code ATMMachine} class.
     */
    @Test
    void testCheckBalance() {
        assertEquals(1000L, atmMachine.checkBalance("1234-5678-9012-3456"));
    }

    /**
     * Tests the {@code withdraw} method of the {@code ATMMachine} class.
     */
    @Test
    void testWithdraw() {
        assertEquals("Успешно снято 500 единиц. Новый баланс: 500", atmMachine.withdraw("1234-5678-9012-3456", 500L));
        assertEquals("Недостаточно средств в банкомате", atmMachine.withdraw("1234-5678-9012-3456", 6000L));
        assertEquals("Недостаточно средств на счете", atmMachine.withdraw("1234-5678-9012-3456", 1000L));
    }

    /**
     * Tests the {@code deposit} method of the {@code ATMMachine} class.
     */
    @Test
    void testDeposit() {
        assertEquals("Успешно пополнено на 500 единиц. Новый баланс: 1500", atmMachine.deposit("1234-5678-9012-3456", 500L));
        assertEquals("Сумма пополнения не должна превышать 1 000 000", atmMachine.deposit("1234-5678-9012-3456", 1_500_000L));
    }

    /**
     * Tests the {@code blockCard} and {@code unblockCard} methods of the {@code ATMMachine} class.
     */
    @Test
    void testBlockAndUnblockCard() {
        atmMachine.blockCard("1234-5678-9012-3456");
        assertTrue(card.isBlocked());

        // Simulate time passing
        card.setBlockTime(System.currentTimeMillis() - 25 * 60 * 60 * 1000);
        atmMachine.unblockCard("1234-5678-9012-3456");
        assertFalse(card.isBlocked());
    }
}
