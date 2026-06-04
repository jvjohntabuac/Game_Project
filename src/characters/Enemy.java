package characters;
//test2

import interfaces.Combatant;

public class Enemy extends GameCharacter {
    private int expReward;
    private String enemyType;
    private String emotion;

    public Enemy(String name, int hp, int atk, int def, int expReward, String enemyType) {
        super(name, hp, atk, def);
        this.expReward = expReward;
        this.enemyType = enemyType;
        this.emotion = "NEUTRAL";
    }

    @Override
    public void attack(Combatant target) {
        System.out.println("\n" + name + " attacks " + target.getName() + "!");
        target.takeDamage(atk);
    }

    @Override
    public void displayStats() {
        System.out.println("\n=== " + name + " ===");
        System.out.println("HP  : " + hp + "/" + maxHP);
        System.out.println("ATK : " + atk);
        System.out.println("DEF : " + def);
        System.out.println("TYPE: " + enemyType);
    }

    @Override
    public String getType() {
        return enemyType;
    }

    public int getExpReward() {
        return expReward;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void reactToHP() {
        if (hp <= maxHP / 4) {
            emotion = "AFRAID";
            System.out.println(name + " is almost defeated... it looks AFRAID!");
        } else if (hp <= maxHP / 2) {
            emotion = "ANGRY";
            System.out.println(name + " is getting desperate... it looks ANGRY!");
        }
    }
}
