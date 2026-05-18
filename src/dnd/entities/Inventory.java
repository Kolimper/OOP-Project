package dnd.entities;


public class Inventory {
    private Item armor;
    private Item weapon;
    private Item spell;

    public Inventory() {}

    public void equip(Item item) {
        if (item.getType() == ItemType.ARMOR) {
            armor = item;
        } else if (item.getType() == ItemType.WEAPON) {
            weapon = item;
        } else if (item.getType() == ItemType.SPELL) {
            spell = item;
        }
    }

   
    public Item getArmor() { return armor; }

    public Item getWeapon() { return weapon; }

    public Item getSpell() { return spell; }

    public int getArmorBonus() {
        return armor != null ? armor.getBonusPercent() : 0;
    }

    public int getWeaponBonus() {
        return weapon != null ? weapon.getBonusPercent() : 0;
    }

    public int getSpellBonus() {
        return spell != null ? spell.getBonusPercent() : 0;
    }

    @Override
    public String toString() {
        String armorStr  = armor  != null ? armor.toString()  : "None";
        String weaponStr = weapon != null ? weapon.toString() : "None";
        String spellStr  = spell  != null ? spell.toString()  : "None";
        return "Armor  : " + armorStr + "\n"
             + "Weapon : " + weaponStr + "\n"
             + "Spell  : " + spellStr;
    }
}
