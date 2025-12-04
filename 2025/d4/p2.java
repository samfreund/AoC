package d4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import utils.Tuple;

class p2 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("2025/d4/input.txt"));

            List<Boolean[]> m = new ArrayList<Boolean[]>();

            while (in.hasNext()) {
                m.add(List.of(in.nextLine().split("")).stream().map(c -> c.matches("@")).toArray(Boolean[]::new));
            }

            in.close();

            int total_count = 0;
            int iter_count;

            do {
                iter_count = 0;
                List<Tuple<Integer, Integer>> tuples = new ArrayList<Tuple<Integer, Integer>>();

                for (int x = 0; x < m.size(); x++) {
                    for (int y = 0; y < m.get(x).length; y++) {
                        if (m.get(x)[y] && accessible(m, x, y, 4)) {
                            iter_count++;
                            tuples.add(new Tuple<Integer, Integer>(x, y));
                        }
                    }

                }

                total_count += iter_count;

                tuples.stream().forEach(t -> m.get(t.x)[t.y] = false);
            } while (iter_count != 0);

            System.out.println(total_count);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * top left is origin
     * if this is called, we expect the value at x,y is true
     * 
     * @param m matrix
     * @param x x-coord
     * @param y y-coord
     * @param n number of allowable adjacent non-empty positions
     * @return
     */
    private static boolean accessible(List<Boolean[]> m, int x, int y, int n) {
        int count = 0;

        for (int x_1 = (x != 0 ? x - 1 : 0); x_1 <= ((x != m.size() - 1) ? x + 1 : m.size() - 1); x_1++) {
            for (int y_1 = (y != 0 ? y - 1 : 0); y_1 <= ((y != m.get(x_1).length - 1) ? y + 1
                    : m.get(x_1).length - 1); y_1++) {
                if (!(x == x_1 && y == y_1) && m.get(x_1)[y_1]) {
                    count++;
                }
            }
        }

        return n > count;
    }
}