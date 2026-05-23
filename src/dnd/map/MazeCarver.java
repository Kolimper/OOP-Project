package dnd.map;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class MazeCarver {

    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 200;

    public static char[][] generate(int rows, int cols) {
        char[][] grid;
        int attempts = 0;
        do {
            grid = buildMaze(rows, cols);
            attempts++;
            if (attempts > MAX_ATTEMPTS) {
                return buildFallback(rows, cols);
            }
        } while (!isSolvable(grid, rows, cols));
        return grid;
    }

    private static char[][] buildMaze(int rows, int cols) {
        char[][] grid = new char[rows][cols];
        for (char[] row : grid) {
            Arrays.fill(row, GameMap.WALL);
        }
        carve(grid, rows, cols);
        placeStartAndExit(grid, rows, cols);
        return grid;
    }

    private static void carve(char[][] grid, int rows, int cols) {
        grid[1][1] = GameMap.FREE;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{1, 1});

        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int r = current[0];
            int c = current[1];

            List<int[]> shuffled = new ArrayList<>(Arrays.asList(directions));
            Collections.shuffle(shuffled, random);

            boolean moved = false;
            for (int[] d : shuffled) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (nr > 0 && nr < rows - 1 && nc > 0 && nc < cols - 1
                        && grid[nr][nc] == GameMap.WALL) {
                    grid[r + d[0] / 2][c + d[1] / 2] = GameMap.FREE;
                    grid[nr][nc] = GameMap.FREE;
                    stack.push(new int[]{nr, nc});
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                stack.pop();
            }
        }
    }

    private static void placeStartAndExit(char[][] grid, int rows, int cols) {
        grid[1][1] = GameMap.START;
        for (int r = rows - 2; r >= 1; r--) {
            for (int c = cols - 2; c >= 1; c--) {
                if (grid[r][c] == GameMap.FREE) {
                    grid[r][c] = GameMap.EXIT;
                    return;
                }
            }
        }
    }

    private static char[][] buildFallback(int rows, int cols) {
        char[][] grid = new char[rows][cols];
        for (char[] row : grid) {
            Arrays.fill(row, GameMap.WALL);
        }
        for (int c = 1; c < cols - 1; c++) grid[1][c] = GameMap.FREE;
        for (int r = 1; r < rows - 1; r++) grid[r][cols - 2] = GameMap.FREE;
        grid[1][1] = GameMap.START;
        grid[rows - 2][cols - 2] = GameMap.EXIT;
        return grid;
    }

    private static boolean isSolvable(char[][] grid, int rows, int cols) {
        int sr = -1, sc = -1, er = -1, ec = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == GameMap.START)     { sr = r; sc = c; }
                else if (grid[r][c] == GameMap.EXIT) { er = r; ec = c; }
            }
        }
        if (sr == -1 || er == -1) return false;

        boolean[][] visited = new boolean[rows][cols];
        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        queue.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            if (p[0] == er && p[1] == ec) return true;
            for (int[] d : dirs) {
                int nr = p[0] + d[0];
                int nc = p[1] + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited[nr][nc] && grid[nr][nc] != GameMap.WALL) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }
}
