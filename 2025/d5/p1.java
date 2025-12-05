package d5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import utils.Tuple;

class p1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("2025/d5/input.txt"));

        List<Tuple<Long, Long>> tuples = new ArrayList<Tuple<Long, Long>>();
        List<Long> l = new LinkedList<Long>();

        while (in.hasNextLine()) {
            String s = in.nextLine();
            if (s == "") {
                break;
            }

            String[] s_split = s.split("-");

            tuples.add(new Tuple<Long, Long>(Long.parseLong(s_split[0]), Long.parseLong(s_split[1])));

        }

        while (in.hasNextLine()) {
            l.add(in.nextLong());
        }

        in.close();

        int count = 0;

        outerloop: for (Long n : l) {
            for (Tuple<Long, Long> t : tuples) {
                if (n >= t.x && n <= t.y) {
                    count++;
                    continue outerloop;
                }
            }
        }

        System.out.println(count);
    }
}