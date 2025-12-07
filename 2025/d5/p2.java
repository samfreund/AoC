// Test answer should be 17

package d5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import utils.Pair;

class p2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("2025/d5/input.txt"));

        List<Pair<Long, Long>> tuples = new ArrayList<Pair<Long, Long>>();

        while (in.hasNextLine()) {
            String s = in.nextLine();
            if (s == "") {
                break;
            }

            String[] s_split = s.split("-");

            tuples.add(new Pair<Long, Long>(Long.parseLong(s_split[0]), Long.parseLong(s_split[1])));

        }

        in.close();

        List<Pair<Long, Long>> fresh = new ArrayList<Pair<Long, Long>>();

        outerloop: for (Pair<Long, Long> t : tuples) {

            Pair<Long, Long> t_filter = t;
            fresh = fresh.stream().filter(f -> !(contains(t_filter, f.x) && contains(t_filter, f.y)))
                    .collect(Collectors.toList());

            for (Pair<Long, Long> t_other : fresh) {
                if (t_other.equals(t)) {
                    continue outerloop;
                }

                boolean start_contained = contains(t_other, t.x);
                boolean end_contained = contains(t_other, t.y);

                if (start_contained && end_contained) {
                    continue outerloop;
                } else if (start_contained) {
                    t = new Pair<Long, Long>(t_other.y + 1, t.y);
                } else if (end_contained) {
                    t = new Pair<Long, Long>(t.x, t_other.x - 1);
                }
            }
            fresh.add(t);
        }

        long count = 0;

        for (Pair<Long, Long> t : fresh) {
            count += t.y - t.x + 1;
        }

        System.out.println(count);
    }

    private static boolean contains(Pair<Long, Long> t, Long n) {
        return n >= t.x && n <= t.y;
    }
}