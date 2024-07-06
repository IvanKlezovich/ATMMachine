package org.example.atmmachine.service;

import org.example.entity.ATMMachine;
import org.example.entity.Card;
import org.example.service.ATMService;
import org.example.service.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The {@code ATMServiceTest} class contains unit tests for the {@code ATMService} class.
 */
class ATMServiceTest {

    @Mock
    private FileHandler fileHandler;

    @Mock
    private ATMMachine atmMachine;

    private ATMService atmService;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atmService = new ATMService("cardsTest.txt", 1000L, 5000L) {
            @Override
            public FileHandler createFileHandler(String filePath) {
                return fileHandler;
            }

            @Override
            public ATMMachine createATMMachine(Long cashLimit, Long moneyBuffer) {
                return atmMachine;
            }
        };
    }

    /**
     * Tests the constructor and initialization of the {@code ATMService} class.
     */
    @Test
    void testConstructorAndInitialization() {
        Map<String, Card> cards = new HashMap<>();
        cards.put("1234-5678-9012-3456", new Card("1234-5678-9012-3456", (short) 1234, 1000L, false, 0L));

        when(fileHandler.loadCards()).thenReturn(cards);

        verify(fileHandler).loadCards();
        verify(atmMachine).addCard(any(Card.class));
    }

    /**
     * Tests the {@code saveState} method of the {@code ATMService} class.
     */
    @Test
    void testSaveState() {
        atmService.saveState();
        verify(fileHandler).saveCards(anyMap());
    }
}


