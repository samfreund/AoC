package d7;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

import utils.Pair;

public class p2 {

    private static int line;

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("2025/d7/input.txt"));

        Pair<Integer, Integer> start;

        start = new Pair<>(0, in.nextLine().indexOf("S"));

        // First line has our start, second line is empty
        line = 1;
        in.nextLine();

        HashSet<Pair<Integer, Integer>> l = new HashSet<>();

        while (in.hasNextLine()) {
            line++;

            String s = in.nextLine();

            int index = s.indexOf("^");
            while (index != -1) {
                l.add(new Pair<Integer, Integer>(line, index));
                s = s.replaceFirst("\\^", "\\.");
                index = s.indexOf("^");
            }
        }

        in.close();


        System.out.println(traverse(l, start.x, start.y));
    }

    private static int traverse(HashSet<Pair<Integer, Integer>> l, int x_start, int y_start) {
        Pair<Integer, Integer> splitter;
        boolean found = false;

        do {
            splitter = new Pair<>(x_start++, y_start);
            if (l.contains(splitter)) {
                found = true;
                break;
            }
        } while (x_start <= line);

        if (found) {
            return traverse(l, splitter.x, splitter.y + 1) +
                traverse(l, splitter.x, splitter.y - 1);
            
        }
        return 1;
    }
}
