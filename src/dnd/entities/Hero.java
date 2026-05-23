package dnd.entities;


public class Hero extends Entity {

    private final Race race;
    private int level;
    private final Inventory inventory;
    private int pendingPoints;

    public Hero(Race race) {
        this.race = race;
        this.level = 1;
        this.strength = race.getBaseStrength();
        this.mana = race.getBaseMana();
        this.health = race.getBaseHealth();
        this.maxHealth = race.getBaseHealth();
        this.inventory = new Inventory();
        this.pendingPoints = 0;

        inventory.equip(new Item("Common Sword", ItemType.WEAPON, 20));
        inventory.equip(new Item("Fireball", ItemType.SPELL, 20));
    }

    public Race getRace() { return race; }

    public int getLevel() { return level; }

    public Inventory getInventory() { return inventory; }

    public int getPendingPoints() { return pendingPoints; }

    @Override
    protected int getArmorPercent() {
        return inventory.getArmorBonus();
    }

    public void restoreHealthAfterVictory() {
        health = maxHealth / 2;
    }

    public void levelUp() {
        level++;
        pendingPoints += 30;
    }

    public boolean allocate(String stat, int points) {
        if (points <= 0 || points > pendingPoints) {
            return false;
        }
        switch (stat.toLowerCase()) {
            case "strength":
                strength += points;
                pendingPoints -= points;
                return true;
            case "mana":
                mana += points;
                pendingPoints -= points;
                return true;
            case "health":
                health += points;
                maxHealth += points;
                pendingPoints -= points;
                return true;
            default:
                return false;
        }
    }

    public boolean allPointsAllocated() {
        return pendingPoints == 0;
    }

    public int getPhysicalAttack() {
        int bonus = inventory.getWeaponBonus();
        return strength + (strength * bonus / 100);
    }

    public int getSpellAttack() {
        int bonus = inventory.getSpellBonus();
        return mana + (mana * bonus / 100);
    }

    @Override
    public String toString() {
        return String.format("Hero [%s Lv.%d | STR=%d MANA=%d HP=%d/%d | pos(%d,%d)]",
                race.getName(), level, strength, mana, health, maxHealth, row, col);
    }
}
