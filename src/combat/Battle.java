package combat;

import characters.Boss;
import characters.Enemy;
import characters.Player;
import java.util.Scanner;

public class Battle {
    private Player currentPlayer;
    private Enemy currentEnemy;
    private Scanner scanner;
    private boolean battleOver;
    private boolean playerWon;

    public Battle(Player currentPlayer, Enemy currentEnemy) {
        this.currentPlayer = currentPlayer;
        this.currentEnemy = currentEnemy;
        this.scanner = new Scanner(System.in);
        this.battleOver = false;
        this.playerWon = false;
    }

    public boolean startBattle() {
        displayBattleStart();

        while (!battleOver) {
            displayBattleStatus();
            playerTurn();

            if (!battleOver) {
                enemyTurn();
            }

            checkBattleOver();
        }

        return playerWon;
    }

    private void displayBattleStart() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       BATTLE START!          ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("\nOMORI vs " + currentEnemy.getName() + "!");
        System.out.println("A battle begins...\n");
    }

    private void displayBattleStatus() {
        System.out.println("\n------------------------------");
        System.out.println("OMORI          " + currentEnemy.getName());
        System.out.println("HP: " + currentPlayer.getHP() + "/" + currentPlayer.getMaxHP()
                + "       HP: " + currentEnemy.getHP() + "/" + currentEnemy.getMaxHP());
        System.out.println("ATK: " + currentPlayer.getAtk()
                + "           ATK: " + currentEnemy.getAtk());
        System.out.println("EMOTION: " + currentPlayer.getEmotion());
        System.out.println("------------------------------");
    }

    private void playerTurn() {
        System.out.println("\nWhat will OMORI do?");
        System.out.println("[1] Attack");
        System.out.println("[2] Use Item");
        System.out.println("[3] Change Emotion");
        System.out.println("[4] Run");

        int choice = getValidChoice(1, 4);

        switch (choice) {
            case 1:
                currentPlayer.attack(currentEnemy);
                currentEnemy.reactToHP();
                break;
            case 2:
                handleItemUse();
                break;
            case 3:
                handleEmotionChange();
                break;
            case 4:
                handleRun();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void handleItemUse() {
        if (currentPlayer.getInventory().isEmpty()) {
            System.out.println("\nInventory is empty!");
            System.out.println("OMORI has nothing to use!");
            playerTurn();
            return;
        }

        currentPlayer.getInventory().displayInventory();
        System.out.println("[0] Cancel");

        int choice = getValidChoice(0, currentPlayer.getInventory().getSize());

        if (choice == 0) {
            playerTurn();
            return;
        }

        currentPlayer.useItem(choice - 1);
    }

    private void handleEmotionChange() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║      CHANGE EMOTION          ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("[1] HAPPY  (ATK +5, DEF -2)");
        System.out.println("[2] SAD    (DEF +5, ATK -2)");
        System.out.println("[3] ANGRY  (ATK +10, DEF -5)");
        System.out.println("[4] AFRAID (DEF +10, ATK -5)");
        System.out.println("[5] NEUTRAL");
        System.out.println("[6] Cancel");

        int choice = getValidChoice(1, 6);

        switch (choice) {
            case 1:
                currentPlayer.changeEmotion("HAPPY");
                break;
            case 2:
                currentPlayer.changeEmotion("SAD");
                break;
            case 3:
                currentPlayer.changeEmotion("ANGRY");
                break;
            case 4:
                currentPlayer.changeEmotion("AFRAID");
                break;
            case 5:
                currentPlayer.changeEmotion("NEUTRAL");
                break;
            case 6:
                playerTurn();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void handleRun() {
        if (currentEnemy instanceof Boss) {
            System.out.println("\nYou cannot run from a boss battle!");
            playerTurn();
            return;
        }

        int chance = (int) (Math.random() * 100);

        if (chance < 50) {
            System.out.println("\nOMORI ran away safely!");
            battleOver = true;
            playerWon = false;
        } else {
            System.out.println("\nOMORI tried to run but failed!");
            System.out.println("The enemy blocks the way!");
        }
    }

    private void enemyTurn() {
        if (currentEnemy.isAlive()) {
            currentEnemy.attack(currentPlayer);
        }
    }

    private void checkBattleOver() {
        if (!currentEnemy.isAlive()) {
            battleOver = true;
            playerWon = true;
            displayVictory();
        } else if (!currentPlayer.isAlive()) {
            battleOver = true;
            playerWon = false;
            displayDefeat();
        }
    }

    private void displayVictory() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║         VICTORY!             ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println(currentEnemy.getName() + " has been defeated!");
        currentPlayer.gainExp(currentEnemy.getExpReward());
    }

    private void displayDefeat() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║         DEFEATED...          ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("OMORI has fallen...");
        System.out.println("The darkness takes over...");
    }

    private int getValidChoice(int min, int max) {
        int choice = -1;

        while (choice < min || choice > max) {
            System.out.print("\nEnter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < min || choice > max) {
                    System.out.println("Please enter " + min + " to " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }

        return choice;
    }
}
