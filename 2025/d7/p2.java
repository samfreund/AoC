package d7;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import utils.Pair;

public class p2 {

    private static int line;
    private static HashMap<Pair<Integer, Integer>, Long> memo = new HashMap<>();

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

        System.out.println(traverse(l, start));
    }

    private static long traverse(HashSet<Pair<Integer, Integer>> l, Pair<Integer, Integer> start) {
        boolean found = false;

        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        int iter = start.x;

        do {
            iter++;
            if (l.contains(new Pair<Integer, Integer>(iter, start.y))) {
                found = true;
                break;
            }
        } while (iter <= line);

        if (found) {
            long ret = traverse(l, new Pair<Integer, Integer>(iter, start.y + 1)) +
                    traverse(l, new Pair<Integer, Integer>(iter, start.y - 1));

            memo.put(start, ret);

            return ret;

        }
        return 1;
    }
}
