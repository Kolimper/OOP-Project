package dnd.map;

import dnd.entities.Monster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoader {

    public static GameMap load(String filePath, int level) throws IOException {
        char[][] grid = readGrid(filePath);
        GameMap map = new GameMap(grid);

        if (!map.isCompletable()) {
            throw new IllegalArgumentException(
                "Map '" + filePath + "' is not completable - no path from S to E.");
        }

        populateFromGrid(map, grid, level);
        return map;
    }

    public static void populateFromGrid(GameMap map, char[][] grid, int level) {
        for (int r = 0; r < map.getRows(); r++) {
            for (int c = 0; c < map.getCols(); c++) {
                if (grid[r][c] == GameMap.MONSTER) {
                    map.placeMonster(r, c, new Monster(level));
                } else if (grid[r][c] == GameMap.TREASURE) {
                    map.placeTreasure(r, c, ItemList.randomItem());
                }
            }
        }
    }

    private static char[][] readGrid(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String firstLine = reader.readLine();
        if (firstLine == null) {
            reader.close();
            throw new IOException("Map file is empty: " + filePath);
        }

        String[] parts = firstLine.trim().split("\\s+");
        if (parts.length < 2) {
            reader.close();
            throw new IOException("Bad first line in map file: " + firstLine);
        }

        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);
        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            String line = reader.readLine();
            if (line == null) {
                reader.close();
                throw new IOException("Map file has fewer rows than declared.");
            }
            if (line.length() < cols) {
                line = line + "#".repeat(cols - line.length());
            } else if (line.length() > cols) {
                line = line.substring(0, cols);
            }
            grid[r] = line.toCharArray();
        }

        reader.close();
        return grid;
    }
}
