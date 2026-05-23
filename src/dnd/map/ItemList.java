package dnd.map;

import dnd.entities.Item;
import dnd.entities.ItemType;

import java.util.Random;

public class ItemList {

    private static final Random random = new Random();

    private static final String[][] ITEM_POOL = {
        {"Iron Shield",    "ARMOR",  "15"},
        {"Steel Shield",   "ARMOR",  "25"},
        {"Dragon Scale",   "ARMOR",  "35"},
        {"Iron Sword",     "WEAPON", "20"},
        {"Battle Axe",     "WEAPON", "30"},
        {"Excalibur",      "WEAPON", "50"},
        {"Fireball",       "SPELL",  "20"},
        {"Lightning Bolt", "SPELL",  "35"},
        {"Blizzard",       "SPELL",  "50"},
    };

    public static Item randomItem() {
        String[] entry = ITEM_POOL[random.nextInt(ITEM_POOL.length)];
        return new Item(entry[0], ItemType.valueOf(entry[1]), Integer.parseInt(entry[2]));
    }
}
