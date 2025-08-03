package service;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import model.Knight;
import model.Sorcerer;
import model.TibiaCharacter;


public class BattleManager {

	 // Logger instance for logging battle events
    private static final Logger logger = Logger.getLogger(BattleManager.class.getName());

    static {
        try {
            // Set up a FileHandler to log to "battle_log.txt"
            FileHandler fileHandler = new FileHandler("battle_log.txt", true); // append = true
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(true); // Keep console logging too
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to set up file logging", e);
        }
    }

    // Starts the battle between two characters using multithreading
    public static void startBattle(TibiaCharacter c1, TibiaCharacter c2) {
        logger.info("Battle Start: " + c1.getName() + " vs " + c2.getName());
        System.out.println();

        // Create a thread for each character's combat loop
        Thread t1 = new Thread(() -> characterCombatLoop(c1, c2));
        Thread t2 = new Thread(() -> characterCombatLoop(c2, c1));

        // Start both threads
        t1.start();
        t2.start();

        // Wait for both threads to complete
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, "Battle interrupted", e);
        }

        // Display result based on remaining health
        if (c1.getHealth() <= 0 && c2.getHealth() <= 0) {
            logger.info("It's a draw!");
            System.out.println("It's a draw!");
        } else if (c1.getHealth() <= 0) {
            logger.info(c2.getName() + " wins!");
            System.out.println(c2.getName() + " wins!");
        } else {
            logger.info(c1.getName() + " wins!");
            System.out.println(c1.getName() + " wins!");
        }
    }

    // Loop that handles one character's turn-based actions in battle
    private static void characterCombatLoop(TibiaCharacter attacker, TibiaCharacter target) {
        int turn = 1;

        // Continue battling while both characters are alive
        while (attacker.getHealth() > 0 && target.getHealth() > 0) {

            // Synchronize output to prevent interleaved prints
            synchronized (BattleManager.class) {
                String actionMsg = "Turn " + turn + " - " + attacker.getName() + "'s action";
                System.out.println(actionMsg);
                logger.info(actionMsg);
            }

            // Special behavior based on character type
            if (attacker instanceof Knight knight) {
                if (turn % 2 == 0) {
                    logger.fine(attacker.getName() + " uses Exori Attack");
                    knight.exoriAttack(target);
                } else {
                    logger.fine(attacker.getName() + " uses Weapon Attack");
                    knight.weaponAttack(target);
                }

                if (turn % 5 == 0) {
                    logger.fine(attacker.getName() + " performs Heal Tick");
                    knight.healTick();
                }

            } else if (attacker instanceof Sorcerer sorcerer) {
                logger.fine(attacker.getName() + " casts Sudden Death");
                sorcerer.suddenDeathAttack(target);

                if (turn % 5 == 0) {
                    logger.fine(attacker.getName() + " performs Heal Tick");
                    sorcerer.healTick();
                }

            } else {
                double damage = 20 + Math.random() * 10;
                target.setHealth(target.getHealth() - damage);

                synchronized (BattleManager.class) {
                    String hitMsg = attacker.getName() + " hits " + target.getName() + " for " + String.format("%.2f", damage) + " damage!";
                    System.out.println(hitMsg);
                    logger.info(hitMsg);
                }
            }

            // Synchronize output for clean health display
            synchronized (BattleManager.class) {
                String statusMsg = String.format("%s HP: %.2f\n%s HP: %.2f\n-------------------------------------------------",
                        attacker.getName(), Math.max(0, attacker.getHealth()),
                        target.getName(), Math.max(0, target.getHealth()));
                System.out.println(statusMsg);
                logger.info(statusMsg);
            }

            if (attacker.getHealth() <= 0 || target.getHealth() <= 0) break;

            try {
                Thread.sleep(2000); // 2 seconds per turn
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Thread sleep interrupted", e);
            }

            turn++;
        }
    }
	
}
