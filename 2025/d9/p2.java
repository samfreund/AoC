package d9;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import utils.Pair;
import java.io.File;

class p2 {

    // 0 is empty, 1 is red, 2 is green
    private static int[][] grid;
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("2025/d9/input.txt"));

        List<Pair<Integer, Integer>> l = new LinkedList<>();

        while (in.hasNextLine()) {
            String[] arr = in.nextLine().split(",");

            l.add(new Pair<Integer, Integer>(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])));
        }

        in.close();

        int biggest_x = l.stream().map(p -> p.x).reduce((a, b) -> a > b ? a : b).get();
        int biggest_y = l.stream().map(p -> p.y).reduce((a, b) -> a > b ? a : b).get();

        grid = new int[biggest_x][biggest_y];

        // Since it wraps, we link the last to the first
        addToGrid(l.getLast(), l.getFirst());

        while(l.size() > 0) {
            Pair<Integer, Integer> first = l.removeFirst();
            Pair<Integer, Integer> second = l.getFirst();

            addToGrid(first, second);
        }
        
    }

    /**
     * Return -1 if it's an invalid rectangle
     * @param first
     * @param second
     * @return
     */
    private static long area(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        long x = Math.abs(first.x - second.x) + 1;
        long y = Math.abs(first.y - second.y) + 1;
        return x * y;
    }

    private static void addToGrid(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        grid[first.x][first.y] = 1;
        grid[second.x][second.y] = 1;

        if (first.x == second.x) {
            for(int i = first.x > second.x ? second.x : first.x; i < (first.x > second.x ? first.x : second.x); i++) {
                if (grid[first.x][i] == 0) {
                    grid[first.x][i] = 2;
                }
            }
        } else if (first.y == second.y) {
            for(int i = first.y > second.y ? second.y : first.y; i < (first.y > second.y ? first.y : second.y); i++) {
                if (grid[i][first.y] == 0){
                    grid[i][first.y] = 2;
                }
            }
        }
    }

}