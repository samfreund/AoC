package d8;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import utils.Triple;
import utils.UnorderedPair;

public class p1 {
    public static void main(String[] args) throws Exception {
        LinkedList<Triple<Integer, Integer, Integer>> l = new LinkedList<>();

        HashMap<UnorderedPair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>>, Double> m = new HashMap<>();

        Scanner in = new Scanner(new File("2025/d8/input.txt"));

        while (in.hasNextLine()) {
            String[] arr = in.nextLine().split(",");

            l.add(new Triple<Integer, Integer, Integer>(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]),
                    Integer.valueOf(arr[2])));
        }

        in.close();

        while (l.size() > 0) {
            Triple<Integer, Integer, Integer> coord = l.removeFirst();

            l.stream().forEach(c -> m.put(
                    new UnorderedPair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>>(coord, c),
                    dist(coord, c)));
        }

        l = null;

        ArrayList<HashSet<Triple<Integer, Integer, Integer>>> circs = new ArrayList<>();

        outerloop: for (int i = 0; i < 1000; i++) {
            UnorderedPair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>> closest = m.entrySet()
                    .stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            m.remove(closest);

            if (circs.size() == 0) {
                HashSet<Triple<Integer, Integer, Integer>> init_l = new HashSet<Triple<Integer, Integer, Integer>>();
                init_l.add(closest.x);
                init_l.add(closest.y);
                circs.add(init_l);
                continue;
            }

            HashSet<Triple<Integer, Integer, Integer>> circ_found = null;

            for (HashSet<Triple<Integer, Integer, Integer>> circ : circs) {
                boolean contains_x = circ.contains(closest.x);
                boolean contains_y = circ.contains(closest.y);
                if (contains_x && contains_y) {
                    continue outerloop;
                } else if (contains_x) {
                    circ.add(closest.y);
                    circ_found = circ;
                    break;
                } else if (contains_y) {
                    circ.add(closest.x);
                    circ_found = circ;
                    break;
                }
            }

            if (circ_found == null) {
                circ_found = new HashSet<Triple<Integer, Integer, Integer>>();
                circ_found.add(closest.x);
                circ_found.add(closest.y);
                circs.add(circ_found);
                continue;
            }

            for (HashSet<Triple<Integer, Integer, Integer>> circ : circs) {
                if (circ.equals(circ_found)) {
                    continue;
                }

                if (circ.contains(closest.x) || circ.contains(closest.y)) {
                    circ.addAll(circ_found);
                    circs.remove(circ_found);
                    break outerloop;
                }

            }
        }

        // Sort circs by size

        Collections.sort(circs, (a, b) -> Integer.compare(b.size(), a.size()));

        long ret = circs.get(0).size() * circs.get(1).size() * circs.get(2).size();


        System.out.println(ret);

    }

    private static double dist(Triple<Integer, Integer, Integer> first, Triple<Integer, Integer, Integer> second) {
        double x = Math.pow(first.x - second.x, 2);
        double y = Math.pow(first.y - second.y, 2);
        double z = Math.pow(first.z - second.z, 2);

        return Math.sqrt(x + y + z);
    }
}
