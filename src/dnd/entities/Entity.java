package dnd.entities;
public abstract class Entity {

    protected int strength;
    protected int mana;
    protected int health;
    protected int maxHealth;
    protected int row;
    protected int col;

    
    public int getStrength() { return strength; }

    
    public int getMana() { return mana; }

    
    public int getHealth() { return health; }

    public int getMaxHealth() { return maxHealth; }

    public boolean isAlive() { return health > 0; }

    public int getRow() { return row; }

    public int getCol() { return col; }

    
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

   
    public int applyArmor(int rawDamage) {
        int reduction = getArmorPercent();
        return rawDamage - (rawDamage * reduction / 100);
    }

    protected abstract int getArmorPercent();
}
