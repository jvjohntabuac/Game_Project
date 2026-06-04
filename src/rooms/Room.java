package rooms;

import characters.Enemy;
import items.Item;
import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private String atmosphere;
    private int dangerLevel;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> lootTable;
    private boolean isCleared;
    private boolean isBossRoom;

    public Room(String name, String description, String atmosphere, int dangerLevel, boolean isBossRoom) {
        this.name = name;
        this.description = description;
        this.atmosphere = atmosphere;
        this.dangerLevel = dangerLevel;
        this.isBossRoom = isBossRoom;
        this.isCleared = false;
        this.enemies = new ArrayList<>();
        this.lootTable = new ArrayList<>();
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void addLoot(Item i) {
        lootTable.add(i);
    }

    public Enemy getNextEnemy() {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                return e;
            }
        }
        return null;
    }

    public boolean allEnemiesDefeated() {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public void displayRoomInfo() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║  " + name);
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  " + description);
        System.out.println("║  Atmosphere: " + atmosphere);
        System.out.print("║  Danger: ");

        for (int i = 0; i < dangerLevel; i++) {
            System.out.print("★");
        }

        System.out.println();

        if (isCleared) {
            System.out.println("║  Status: CLEARED ✓");
        } else {
            System.out.println("║  Status: NOT CLEARED");
        }

        if (isBossRoom) {
            System.out.println("║  ⚠ BOSS ROOM ⚠");
        }

        System.out.println("╚══════════════════════════════╝");
    }

    public String getDangerStars() {
        StringBuilder stars = new StringBuilder();

        for (int i = 0; i < dangerLevel; i++) {
            stars.append("★");
        }

        return stars.toString();
    }

    public Item getRandomLoot() {
        if (lootTable.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * lootTable.size());
        return lootTable.get(randomIndex);
    }

    public void clearRoom() {
        isCleared = true;
        System.out.println("\n" + name + " has been cleared!");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public boolean isBossRoom() {
        return isBossRoom;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Item> getLootTable() {
        return lootTable;
    }
}
