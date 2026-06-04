package interfaces;

public interface Combatant {
    void attack(Combatant target);
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
    int getHP();
    int getMaxHP();
}
