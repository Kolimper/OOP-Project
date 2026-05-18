package dnd.entities;

public class Monster extends Entity {

    private static final int BASE_STR   = 25;
    private static final int BASE_MANA  = 25;
    private static final int BASE_HP    = 50;
    private static final int BASE_ARMOR = 15;

    private final int armorPercent;

    public Monster(int level) {
        int bonus = (level - 1) * 10;
        this.strength    = BASE_STR  + bonus;
        this.mana        = BASE_MANA + bonus;
        this.health      = BASE_HP   + bonus;
        this.maxHealth   = this.health;
        this.armorPercent = BASE_ARMOR + (level - 1) * 5;
    }

    @Override
    protected int getArmorPercent() {
        return armorPercent;
    }

    public int attack(boolean useSpell) {
        return useSpell ? mana : strength;
    }

    @Override
    public String toString() {
        return String.format("Monster [STR=%d MANA=%d HP=%d/%d Armor=%d%%]",
                strength, mana, health, maxHealth, armorPercent);
    }
}
