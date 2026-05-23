package dnd.map;

public class LevelScaling {

    public static int[] getDimensions(int level) {
        if (level == 1) return new int[]{10, 10};
        if (level == 2) return new int[]{15, 10};
        int[] prev1 = getDimensions(level - 1);
        int[] prev2 = getDimensions(level - 2);
        return new int[]{prev1[0] + prev2[0], prev1[1] + prev2[1]};
    }

    public static int getMonsterCount(int level) {
        if (level == 1) return 2;
        if (level == 2) return 3;
        return getMonsterCount(level - 1) + getMonsterCount(level - 2);
    }

    public static int getTreasureCount(int level) {
        if (level == 1) return 2;
        if (level == 2) return 2;
        return getTreasureCount(level - 1) + getTreasureCount(level - 2);
    }
}
