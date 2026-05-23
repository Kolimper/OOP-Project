package dnd.map;

import dnd.entities.Item;
import dnd.entities.Monster;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GameMap {
    public static final char WALL     = '#';
    public static final char FREE     = '.';
    public static final char START    = 'S';
    public static final char EXIT     = 'E';
    public static final char MONSTER  = 'M';
    public static final char TREASURE = 'T';
    public static final char HERO     = 'H';

    private final char[][] grid;
    private final int rows;
    private final int cols;
    private int startRow;
    private int startCol;
    private int exitRow;
    private int exitCol;

    private final Map<Integer, Item> treasures = new HashMap<>();
    private final Map<Integer, Monster> monsters = new HashMap<>();

    public GameMap(char[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        this.grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            this.grid[r] = Arrays.copyOf(grid[r], cols);
        }
        findStartAndExit();
    }

    private void findStartAndExit() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == START) {
                    startRow = r;
                    startCol = c;
                } else if (grid[r][c] == EXIT) {
                    exitRow = r;
                    exitCol = c;
                }
            }
        }
    }

    public int getRows() { return rows; }

    public int getCols() { return cols; }

    public int getStartRow() { return startRow; }

    public int getStartCol() { return startCol; }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, char cell) {
        grid[row][col] = cell;
    }

   
    public boolean isWalkable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return grid[row][col] != WALL;
    }

    public boolean isExit(int row, int col) {
        return row == exitRow && col == exitCol;
    }

    public void placeTreasure(int row, int col, Item item) {
        treasures.put(cellKey(row, col), item);
        grid[row][col] = TREASURE;
    }

    public void placeMonster(int row, int col, Monster monster) {
        monster.setPosition(row, col);
        monsters.put(cellKey(row, col), monster);
        grid[row][col] = MONSTER;
    }

    public Item pickupTreasure(int row, int col) {
        Item item = treasures.remove(cellKey(row, col));
        if (item != null) {
            grid[row][col] = FREE;
        }
        return item;
    }

    public Monster getMonster(int row, int col) {
        return monsters.get(cellKey(row, col));
    }

    public void removeMonster(int row, int col) {
        monsters.remove(cellKey(row, col));
        grid[row][col] = FREE;
    }

    public boolean isCompletable() {
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0];
            int c = pos[1];
            if (r == exitRow && c == exitCol) {
                return true;
            }
            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited[nr][nc] && grid[nr][nc] != WALL) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    public String render(int heroRow, int heroCol) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == heroRow && c == heroCol) {
                    sb.append(HERO);
                } else {
                    sb.append(grid[r][c]);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private int cellKey(int row, int col) {
        return row * cols + col;
    }
}
