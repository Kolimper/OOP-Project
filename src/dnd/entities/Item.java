package dnd.entities;


public class Item {
    private final String name;
    private final ItemType type;
    private final int bonusPercent;

   
    public Item(String name, ItemType type, int bonusPercent) {
        this.name = name;
        this.type = type;
        this.bonusPercent = bonusPercent;
    }

    public String getName() { return name; }

    public ItemType getType() { return type; }

    public int getBonusPercent() { return bonusPercent; }

    @Override
    public String toString() {
        return name + " (" + type + ", +" + bonusPercent + "%)";
    }
}
