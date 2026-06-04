package characters;
//just a test 
import interfaces.Combatant;

public class Boss extends Enemy {
    private String specialAttack;
    private boolean specialUsed;
    private int phase;

    public Boss(String name, int hp, int atk, int def, int expReward, String specialAttack) {
        super(name, hp, atk, def, expReward, "BOSS");
        this.specialAttack = specialAttack;
        this.specialUsed = false;
        this.phase = 1;
    }

    public void useSpecialAttack(Combatant target) {
        System.out.println("\n" + name + " glows with dark energy...");
        System.out.println(name + " uses " + specialAttack + "!");
        target.takeDamage(atk * 2);
        specialUsed = true;
    }

    @Override
    public void attack(Combatant target) {
        updatePhase();

        if (phase == 2 && !specialUsed) {
            useSpecialAttack(target);
        } else {
            System.out.println("\n" + name + " attacks " + target.getName() + "!");
            target.takeDamage(atk);
            specialUsed = false;
        }
    }

    private void updatePhase() {
        if (hp <= maxHP / 2 && phase == 1) {
            phase = 2;
            System.out.println("\n" + name + " enters PHASE 2!");
            System.out.println(name + " becomes more powerful!");
            atk = (int) (atk * 1.5);
        }
    }

    @Override
    public void displayStats() {
        System.out.println("\n=== BOSS: " + name + " ===");
        System.out.println("HP    : " + hp + "/" + maxHP);
        System.out.println("ATK   : " + atk);
        System.out.println("DEF   : " + def);
        System.out.println("PHASE : " + phase);
        System.out.println("SPEC  : " + specialAttack);
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public int getPhase() {
        return phase;
    }
}
