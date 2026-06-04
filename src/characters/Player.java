package characters;

import interfaces.Combatant;
import interfaces.Saveable;
import items.Inventory;
import items.Item;

public class Player extends GameCharacter implements Saveable {
    private String emotion;
    private int exp;
    private int expToLevel;
    private int level;
    private Inventory playerInventory;
    private int baseAtk;
    private int baseDef;

    public Player(String name) {
        super(name, 100, 10, 5);
        this.emotion = "NEUTRAL";
        this.exp = 0;
        this.expToLevel = 100;
        this.level = 1;
        this.baseAtk = 10;
        this.baseDef = 5;
        this.playerInventory = new Inventory();
        addStartingItems();
    }

    private void addStartingItems() {
        playerInventory.addItem(new Item("Juice Box", "Restores 20 HP", "heal", 20));
        playerInventory.addItem(new Item("Bandage", "Restores 40 HP", "heal", 40));
        playerInventory.addItem(new Item("Candy", "Boosts ATK by 3", "atkBoost", 3));
    }

    @Override
    public void attack(Combatant target) {
        System.out.println("\nOMORI attacks " + target.getName() + "!");
        target.takeDamage(atk);
    }

    public boolean useItem(int index) {
        Item selectedItem = playerInventory.getItem(index);

        if (selectedItem == null) {
            System.out.println("Invalid item!");
            return false;
        }

        applyItem(selectedItem);
        playerInventory.removeItem(selectedItem.getName());
        return true;
    }

    private void applyItem(Item selectedItem) {
        switch (selectedItem.getType()) {
            case "heal":
                int healed = Math.min(selectedItem.getValue(), maxHP - hp);
                hp += healed;
                System.out.println("OMORI used " + selectedItem.getName() + "!");
                System.out.println("Restored " + healed + " HP!");
                System.out.println("HP: " + hp + "/" + maxHP);
                break;

            case "atkBoost":
                atk += selectedItem.getValue();
                System.out.println("OMORI used " + selectedItem.getName() + "!");
                System.out.println("ATK boosted to " + atk + "!");
                break;

            case "defBoost":
                def += selectedItem.getValue();
                System.out.println("OMORI used " + selectedItem.getName() + "!");
                System.out.println("DEF boosted to " + def + "!");
                break;

            default:
                System.out.println("This item has no effect.");
                break;
        }
    }

    public void changeEmotion(String newEmotion) {
        atk = baseAtk;
        def = baseDef;

        this.emotion = newEmotion;

        switch (newEmotion) {
            case "HAPPY":
                atk += 5;
                def -= 2;
                System.out.println("OMORI feels HAPPY!");
                System.out.println("ATK +5, DEF -2");
                break;

            case "SAD":
                def += 5;
                atk -= 2;
                System.out.println("OMORI feels SAD...");
                System.out.println("DEF +5, ATK -2");
                break;

            case "ANGRY":
                atk += 10;
                def -= 5;
                System.out.println("OMORI feels ANGRY!");
                System.out.println("ATK +10, DEF -5");
                break;

            case "AFRAID":
                def += 10;
                atk -= 5;
                System.out.println("OMORI feels AFRAID...");
                System.out.println("DEF +10, ATK -5");
                break;

            case "NEUTRAL":
                System.out.println("OMORI feels NEUTRAL.");
                break;

            default:
                System.out.println("Unknown emotion.");
                break;
        }
    }

    public void gainExp(int amount) {
        exp += amount;
        System.out.println("OMORI gained " + amount + " EXP!");
        System.out.println("EXP: " + exp + "/" + expToLevel);

        if (exp >= expToLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        exp = exp - expToLevel;
        expToLevel = level * 100;
        maxHP += 20;
        hp = maxHP;
        baseAtk += 3;
        baseDef += 2;
        atk = baseAtk;
        def = baseDef;

        System.out.println("\n★ OMORI leveled up! ★");
        System.out.println("Level: " + level);
        System.out.println("HP fully restored!");
        System.out.println("ATK: " + atk);
        System.out.println("DEF: " + def);
    }

    @Override
    public void displayStats() {
        System.out.println("\n╔══════════════════════╗");
        System.out.println("║     OMORI STATS      ║");
        System.out.println("╠══════════════════════╣");
        System.out.println("║ Level  : " + level);
        System.out.println("║ HP     : " + hp + "/" + maxHP);
        System.out.println("║ ATK    : " + atk);
        System.out.println("║ DEF    : " + def);
        System.out.println("║ EXP    : " + exp + "/" + expToLevel);
        System.out.println("║ EMOTION: " + emotion);
        System.out.println("╚══════════════════════╝");
    }

    @Override
    public String getType() {
        return "PLAYER";
    }

    @Override
    public String toSaveString() {
        return name + "," + hp + "," + maxHP + ","
                + atk + "," + def + "," + exp + ","
                + expToLevel + "," + level + ","
                + emotion + "," + baseAtk + "," + baseDef;
    }

    @Override
    public void loadFromString(String data) {
        String[] parts = data.split(",");

        this.name = parts[0];
        this.hp = Integer.parseInt(parts[1]);
        this.maxHP = Integer.parseInt(parts[2]);
        this.atk = Integer.parseInt(parts[3]);
        this.def = Integer.parseInt(parts[4]);
        this.exp = Integer.parseInt(parts[5]);
        this.expToLevel = Integer.parseInt(parts[6]);
        this.level = Integer.parseInt(parts[7]);
        this.emotion = parts[8];
        this.baseAtk = Integer.parseInt(parts[9]);
        this.baseDef = Integer.parseInt(parts[10]);
    }

    public String getEmotion() {
        return emotion;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getExpToLevel() {
        return expToLevel;
    }

    public Inventory getInventory() {
        return playerInventory;
    }
}
