// Import necessary Java Util and I/O classes
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SlidingPuzzleSolver {
    private static char[][] map; // 2D array to store the map
    private static int rows, cols; // Number of rows and columns in the map
    private static Cell start, finish; // Start and finish cells

    public static void main(String[] args) {
        readMapFromFile("./benchmark_series/puzzle_10.txt"); // Read the map from file
        findStartAndFinish(); // Find the start and finish cells in the map

        long startTime = System.nanoTime(); // Measure start time

        Cell solution = shortestPath(); // Find the shortest path from start to finish

        long endTime = System.nanoTime(); // Measure end time
        double executionTime = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

        printSolutionSteps(solution); // Print the solution steps
        System.out.printf("Execution Time: %.6f seconds%n", executionTime); // Display execution time to 6 decimal places
    }

    // Read the map from a file
    private static void readMapFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            rows = 0;
            // Count the number of rows and determine the number of columns
            while (scanner.hasNextLine()) {
                rows++;
                String line = scanner.nextLine();
                if (cols == 0) cols = line.length();
            }
            scanner.close();
            // Initialize the map array
            map = new char[rows][cols];
            scanner = new Scanner(new File(filename));
            // Populate the map array with characters from the file
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < cols; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Find the start and finish cells in the map
    private static void findStartAndFinish() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 'S') {
                    start = new Cell(j, i, "", null);
                } else if (map[i][j] == 'F') {
                    finish = new Cell(j, i, "", null);
                }
            }
        }
    }

    // Find the shortest path from start to finish using BFS
    private static Cell shortestPath() {
        boolean[][] visited = new boolean[rows][cols]; // Array to mark visited cells
        Queue<Cell> queue = new ArrayDeque<>(); // Queue for BFS traversal
        queue.offer(start); // Add the start cell to the queue
        visited[start.y][start.x] = true; // Mark start cell as visited

        int[] dx = {0, 0, 1, -1}; // Offsets for x direction
        int[] dy = {1, -1, 0, 0}; // Offsets for y direction
        String[] directionSymbol = {"Down", "Up", "Right", "Left"}; // Direction symbols

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            Cell current = queue.poll(); // Get the current cell from the queue
            if (current.x == finish.x && current.y == finish.y) {
                return current; // If finish cell is reached, return the current cell
            }
            // Explore adjacent cells in all four directions
            for (int i = 0; i < 4; i++) {
                int nx = current.x;
                int ny = current.y;
                // Move in the current direction until hitting a wall or a rock
                while (true) {
                    nx += dx[i];
                    ny += dy[i];
                    if (!isValid(nx, ny) || map[ny][nx] == '0') {
                        break; // If the next cell is invalid or a rock, stop moving
                    }
                    if (map[ny][nx] == 'F') {
                        return new Cell(nx, ny, directionSymbol[i], current); // If finish cell is found, return the current cell
                    }
                }
                nx -= dx[i];
                ny -= dy[i];
                // If the next cell is not visited, add it to the queue
                if (!visited[ny][nx]) {
                    queue.offer(new Cell(nx, ny, directionSymbol[i], current));
                    visited[ny][nx] = true; // Mark the next cell as visited
                }
            }
        }
        return null; // If no solution is found, return null
    }

    // Check if the given coordinates are valid within the map boundaries
    private static boolean isValid(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    // Print the solution steps
    private static void printSolutionSteps(Cell solution) {
        if (solution == null) {
            System.out.println("No solution found."); // If no solution is found, print a message
            return;
        }
        Stack<String> steps = new Stack<>();
        // Trace back the path from finish to start and push each step onto the stack
        while (solution.parent != null) {
            steps.push("Move " + solution.direction + " to (" + (solution.x + 1) + ", " + (solution.y + 1) + ")");
            solution = solution.parent;
        }
        steps.push("Start at (" + (start.x + 1) + ", " + (start.y + 1) + ")");
        int stepNumber = 1;
        // Pop each step from the stack and print it along with the step number
        while (!steps.isEmpty()) {
            System.out.println(stepNumber++ + ". " + steps.pop());
        }
        System.out.printf("%d. Done !%n", stepNumber); // Print "Done!" with step number
    }
}