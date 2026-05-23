package dnd.map;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapGenerator {

    private static final Random random = new Random();

    public static String generateMapFile(int level, String directory) throws IOException {
        int[] dims = LevelScaling.getDimensions(level);
        int rows = dims[0];
        int cols = dims[1];

        char[][] grid = MazeCarver.generate(rows, cols);
        placeEntities(grid, rows, cols,
                LevelScaling.getMonsterCount(level),
                LevelScaling.getTreasureCount(level));

        String path = directory + java.io.File.separator + "level_" + level + ".txt";
        writeToFile(grid, rows, cols, path);
        return path;
    }

    private static void placeEntities(char[][] grid, int rows, int cols,
                                      int numMonsters, int numTreasures) {
        List<int[]> freeCells = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == GameMap.FREE) {
                    freeCells.add(new int[]{r, c});
                }
            }
        }
        Collections.shuffle(freeCells, random);

        int i = 0;
        for (int m = 0; m < numMonsters && i < freeCells.size(); m++, i++) {
            grid[freeCells.get(i)[0]][freeCells.get(i)[1]] = GameMap.MONSTER;
        }
        for (int t = 0; t < numTreasures && i < freeCells.size(); t++, i++) {
            grid[freeCells.get(i)[0]][freeCells.get(i)[1]] = GameMap.TREASURE;
        }
    }

    private static void writeToFile(char[][] grid, int rows, int cols, String path)
            throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(path));
        pw.println(rows + " " + cols);
        for (int r = 0; r < rows; r++) {
            pw.println(new String(grid[r]));
        }
        pw.close();
    }
}
