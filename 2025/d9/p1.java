package d9;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import utils.Pair;
import java.io.File;

class p1 {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("2025/d9/input.txt"));

        List<Pair<Integer, Integer>> l = new LinkedList<>();

        while (in.hasNextLine()) {
            String[] arr = in.nextLine().split(",");

            l.add(new Pair<Integer, Integer>(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])));
        }

        in.close();

        long biggest = 0;

        while (l.size() > 0) {
            Pair<Integer, Integer> comp = l.removeFirst();

            for (Pair<Integer, Integer> p : l) {
                long area = area(comp, p);
                if (area > biggest){
                    biggest = area;
                }
            }
        }
        System.out.println(biggest);
    }

    private static long area(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        long x = Math.abs(first.x - second.x) + 1;
        long y = Math.abs(first.y - second.y) + 1;
        return x * y;
    }
}