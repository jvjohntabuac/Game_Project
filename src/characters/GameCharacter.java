package characters;

import interfaces.Combatant;

public abstract class GameCharacter implements Combatant {
    protected String name;
    protected int hp;
    protected int maxHP;
    protected int atk;
    protected int def;

    public GameCharacter(String name, int hp, int atk, int def) {
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.atk = atk;
        this.def = def;
    }

    @Override
    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage - def);
        hp = Math.max(0, hp - actualDamage);
        System.out.println(name + " took " + actualDamage + " damage!");
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public int getMaxHP() {
        return maxHP;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setHP(int hp) {
        this.hp = Math.min(hp, maxHP);
    }

    public abstract void displayStats();

    public abstract String getType();
}
