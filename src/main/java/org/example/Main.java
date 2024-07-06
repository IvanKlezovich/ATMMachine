package org.example;

import org.example.entity.ATMMachine;
import org.example.entity.Card;
import org.example.service.ATMService;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The {@code Main} class is the entry point of the ATM application.
 * It handles user interaction with the ATM machine.
 */
public class Main {

    /**
     * The main method that starts the ATM application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        ATMService service = new ATMService("cards.txt", 2000L, 10_000L);
        ATMMachine atm = service.getAtm();
        Scanner scanner = new Scanner(System.in);
        String cardNumber = null;
        int pinAttempts = 0;
        Logger log = Logger.getLogger(Main.class.getName());

        while (atm.getMoneyBuffer() != 0) {
            if (cardNumber == null) {
                log.info("Введите номер карты (формат XXXX-XXXX-XXXX-XXXX): ");
                cardNumber = scanner.nextLine();
                if (!atm.validateCardNumber(cardNumber)) {
                    log.warning("Неверный номер карты");
                    cardNumber = null;
                    continue;
                }
            }

            Card card = atm.getCard(cardNumber);
            if (card.isBlocked()) {
                atm.unblockCard(cardNumber);
                if (card.isBlocked()) {
                    log.warning("Карта заблокирована. Попробуйте позже.");
                    cardNumber = null;
                    continue;
                }
            }

            log.info("Введите ПИН-код: ");
            String pin = scanner.nextLine();
            if (!atm.validatePin(cardNumber, Short.parseShort(pin))) {
                pinAttempts++;
                if (pinAttempts >= 3) {
                    atm.blockCard(cardNumber);
                    log.warning("Карта заблокирована из-за трех неправильных попыток ввода ПИН-кода.");
                    service.saveState();
                    cardNumber = null;
                    pinAttempts = 0;
                } else {
                    log.warning("Неверный ПИН-код");
                }
                continue;
            }

            pinAttempts = 0;
            boolean repetiteur = true;
            while (repetiteur) {
                log.info("""
                        1. Проверить баланс
                        2. Снять средства
                        3. Пополнить баланс
                        4. Выйти
                        Выберите действие:""");
                String choice = scanner.next();

                switch (choice) {
                    case "1":
                        long balance = atm.checkBalance(cardNumber);
                        log.log(Level.INFO, "Текущий баланс: {0}", balance);
                        break;
                    case "2":
                        log.info("Введите сумму для снятия: ");
                        long amountToWithdraw = scanner.nextLong();
                        String resultWithdraw = atm.withdraw(cardNumber, amountToWithdraw);
                        log.info(resultWithdraw);
                        break;
                    case "3":
                        log.info("Введите сумму для пополнения: ");
                        long amountToDeposit = scanner.nextLong();
                        String resultDeposit = atm.deposit(cardNumber, amountToDeposit);
                        log.info(resultDeposit);
                        break;
                    case "4":
                        service.saveState();
                        cardNumber = null;
                        break;
                    default:
                        log.warning("Неверный выбор. Попробуйте снова.");
                }

                if (choice.equals("4")) {
                    repetiteur = false;
                }
            }
        }
    }
}