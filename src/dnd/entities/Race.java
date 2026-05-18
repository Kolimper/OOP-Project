package dnd.entities;

public enum Race {
    HUMAN("Human", 30, 20, 50),
    MAGE("Mage", 10, 40, 50),
    WARRIOR("Warrior", 40, 10, 50);

    private final String name;
    private final int baseStrength;
    private final int baseMana;
    private final int baseHealth;

    Race(String name, int baseStrength, int baseMana, int baseHealth) {
        this.name = name;
        this.baseStrength = baseStrength;
        this.baseMana = baseMana;
        this.baseHealth = baseHealth;
    }

    public String getName() { return name; }

    public int getBaseStrength() { return baseStrength; }

    public int getBaseMana() { return baseMana; }

    public int getBaseHealth() { return baseHealth; }
}
