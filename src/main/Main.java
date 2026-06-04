package main;

import characters.Boss;
import characters.Enemy;
import characters.Player;
import combat.Battle;
import items.Item;
import java.util.ArrayList;
import java.util.Scanner;
import rooms.Room;
import saveload.SaveLoadManager;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Player omori;
    private static ArrayList<Room> rooms;
    private static SaveLoadManager saveManager;
    private static int currentRoomIndex = 0;

    public static void main(String[] args) {
        saveManager = new SaveLoadManager();
        showTitleScreen();
    }

    private static void showTitleScreen() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║                              ║");
        System.out.println("║         O M O R I            ║");
        System.out.println("║                              ║");
        System.out.println("║      WHITE SPACE RPG         ║");
        System.out.println("║                              ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("\n[1] New Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Quit");

        int choice = getValidChoice(1, 3);

        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                loadGame();
                break;
            case 3:
                quitGame();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private static void startNewGame() {
        System.out.println("\nYou wake up in WHITE SPACE...");
        System.out.println("Everything is white.");
        System.out.println("Everything is calm.");
        System.out.println("You are OMORI.\n");

        omori = new Player("OMORI");
        setupRooms();
        pressEnterToContinue();
        showMainHub();
    }

    private static void loadGame() {
        if (!saveManager.saveExists()) {
            System.out.println("\nNo save file found!");
            System.out.println("Starting new game...");
            pressEnterToContinue();
            startNewGame();
            return;
        }

        omori = new Player("OMORI");
        setupRooms();

        currentRoomIndex = saveManager.loadGame(omori);

        if (currentRoomIndex == -1) {
            currentRoomIndex = 0;
        }

        pressEnterToContinue();
        showMainHub();
    }

    private static void setupRooms() {
        rooms = new ArrayList<>();

        Room forest = new Room(
                "Room 1 - The Dark Forest",
                "Trees that whisper your name...",
                "Eerie and dark",
                1,
                false
        );

        forest.addEnemy(new Enemy("MEWO", 40, 6, 2, 30, "SHADOW"));
        forest.addEnemy(new Enemy("SHADOW CAT", 35, 5, 3, 25, "SHADOW"));
        forest.addLoot(new Item("Juice Box", "Restores 20 HP", "heal", 20));
        forest.addLoot(new Item("Bandage", "Restores 40 HP", "heal", 40));
        rooms.add(forest);

        Room library = new Room(
                "Room 2 - The Lost Library",
                "Books that should not be read...",
                "Mysterious and quiet",
                2,
                false
        );

        library.addEnemy(new Enemy("LOST WORDS", 60, 10, 5, 50, "PHANTOM"));
        library.addEnemy(new Enemy("LIBRARIAN", 80, 12, 6, 60, "PHANTOM"));
        library.addLoot(new Item("Candy", "Boosts ATK by 3", "atkBoost", 3));
        library.addLoot(new Item("Bandage", "Restores 40 HP", "heal", 40));
        rooms.add(library);

        Room castle = new Room(
                "Room 3 - The Sunken Castle",
                "Something ancient waits inside...",
                "Dark and overwhelming",
                3,
                true
        );

        castle.addEnemy(new Boss("SOMETHING", 200, 20, 10, 150, "DARK GRASP"));
        castle.addLoot(new Item("Something's Essence", "Boosts DEF by 10", "defBoost", 10));
        rooms.add(castle);
    }

    private static void showMainHub() {
        boolean playing = true;

        while (playing) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║        WHITE SPACE           ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("You see doors in the distance...");
            System.out.println("Which path will you take?\n");

            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);

                System.out.println("[" + (i + 1) + "] " + r.getName());
                System.out.println("    " + r.getDescription());
                System.out.println("    Danger: " + r.getDangerStars());

                if (r.isCleared()) {
                    System.out.println("    Status: CLEARED ✓");
                } else {
                    System.out.println("    Status: NOT CLEARED");
                }

                System.out.println();
            }

            System.out.println("[4] Check Stats");
            System.out.println("[5] Check Inventory");
            System.out.println("[6] Save Game");
            System.out.println("[7] Quit to Title");

            int choice = getValidChoice(1, 7);

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    enterRoom(choice - 1);
                    break;

                case 4:
                    omori.displayStats();
                    pressEnterToContinue();
                    break;

                case 5:
                    omori.getInventory().displayInventory();
                    pressEnterToContinue();
                    break;

                case 6:
                    saveManager.saveGame(omori, currentRoomIndex);
                    pressEnterToContinue();
                    break;

                case 7:
                    playing = false;
                    showTitleScreen();
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            if (allRoomsCleared()) {
                showEnding();
                playing = false;
            }
        }
    }

    private static void enterRoom(int roomIndex) {
        Room selectedRoom = rooms.get(roomIndex);
        currentRoomIndex = roomIndex;
        selectedRoom.displayRoomInfo();

        if (selectedRoom.isCleared()) {
            System.out.println("\nThis room is already cleared!");
            System.out.println("Nothing left to fight here.");
            pressEnterToContinue();
            return;
        }

        if (selectedRoom.isBossRoom()) {
            System.out.println("\n⚠ WARNING: BOSS ROOM ⚠");
            System.out.println("Are you sure you want to enter?");
            System.out.println("[1] Enter");
            System.out.println("[2] Go back");

            int confirm = getValidChoice(1, 2);

            if (confirm == 2) {
                return;
            }
        }

        System.out.println("\nYou enter " + selectedRoom.getName() + "...");

        while (!selectedRoom.allEnemiesDefeated()) {
            Enemy nextEnemy = selectedRoom.getNextEnemy();

            if (nextEnemy == null) {
                break;
            }

            System.out.println("\nA wild " + nextEnemy.getName() + " appears!");

            Battle currentBattle = new Battle(omori, nextEnemy);
            boolean won = currentBattle.startBattle();

            if (!won && !omori.isAlive()) {
                boolean retry = askRetryBattle();

                if (retry) {
                    omori.setHP(omori.getMaxHP());
                    nextEnemy.setHP(nextEnemy.getMaxHP());
                    System.out.println("\nOMORI gathers strength and retries the battle...");
                    pressEnterToContinue();
                    continue;
                } else {
                    gameOver();
                    return;
                }

            } else if (won) {
                Item loot = selectedRoom.getRandomLoot();

                if (loot != null) {
                    System.out.println("\nOMORI found " + loot.getName() + "!");
                    omori.getInventory().addItem(loot);
                    pressEnterToContinue();
                }
            } else {
                System.out.println("\nOMORI escaped the battle.");
                pressEnterToContinue();
                return;
            }
        }

        if (selectedRoom.allEnemiesDefeated()) {
            selectedRoom.clearRoom();
            pressEnterToContinue();
        }
    }

    private static boolean askRetryBattle() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("[1] Retry Battle");
        System.out.println("[2] Return to Title");

        int choice = getValidChoice(1, 2);

        return choice == 1;
    }

    private static boolean allRoomsCleared() {
        for (Room r : rooms) {
            if (!r.isCleared()) {
                return false;
            }
        }

        return true;
    }

    private static void gameOver() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║         GAME OVER            ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("The darkness consumed OMORI...");
        System.out.println("Everything is black.");
        System.out.println("\n[1] Try Again");
        System.out.println("[2] Quit to Title");

        int choice = getValidChoice(1, 2);

        if (choice == 1) {
            startNewGame();
        } else {
            showTitleScreen();
        }
    }

    private static void showEnding() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║      CONGRATULATIONS!        ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("OMORI has cleared WHITE SPACE!");
        System.out.println("The darkness has been defeated.");
        System.out.println("\nFinal Stats:");
        omori.displayStats();
        System.out.println("\nThank you for playing!");
        System.out.println("\n[1] Play Again");
        System.out.println("[2] Quit");

        int choice = getValidChoice(1, 2);

        if (choice == 1) {
            startNewGame();
        } else {
            quitGame();
        }
    }

    private static void quitGame() {
        System.out.println("\nGoodbye...");
        System.out.println("WHITE SPACE fades away...");
        System.exit(0);
    }

    private static void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static int getValidChoice(int min, int max) {
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
