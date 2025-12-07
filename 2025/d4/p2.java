package d4;

import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import utils.Pair;

class p2 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("2025/d4/input.txt"));

            HashSet<Pair<Integer, Integer>> l = new HashSet<Pair<Integer, Integer>>();

            for (int row = 0; in.hasNext(); row++) {
                String s = in.nextLine();
                for (int col = 0; col < s.length(); col++) {
                    if (s.charAt(col) == '@') {
                        l.add(new Pair<Integer, Integer>(row, col));
                    }
                }
            }

            in.close();

            int total_count = 0;
            int iter_count;

            do {
                iter_count = 0;
                Iterator<Pair<Integer, Integer>> it = l.iterator();
                while (it.hasNext()) {
                    Pair<Integer, Integer> t = it.next();
                    if (accessible(l, t, 4)) {
                        iter_count++;
                        it.remove();
                    }
                }

                total_count += iter_count;
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
     * @param l matrix
     * @param x x-coord
     * @param y y-coord
     * @param n number of allowable adjacent non-empty positions
     * @return
     */
    private static boolean accessible(HashSet<Pair<Integer, Integer>> l, Pair<Integer, Integer> t, int n) {
        int count = 0;

        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (row == 0 && col == 0) {
                    continue;
                }

                Pair<Integer, Integer> t_other = new Pair<Integer, Integer>(t.x + row, t.y + col);

                if (l.contains(t_other)) {
                    count++;
                }
            }
        }

        return n > count;
    }
}