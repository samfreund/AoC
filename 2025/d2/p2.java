package d2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class p2 {
    public static void main(String[] args) {
        long test = 4174379265L;

        try {
            Scanner in = new Scanner(new File("2025/d2/input.txt"));

            String line = in.nextLine();
            in.close();

            List<Long> invalids = new LinkedList<>();

            for (String s : line.split(",")) {
                String[] split = s.split("-");

                for (Long i = Long.parseLong(split[0]); i <= Long.parseLong(split[1]); i++) {
                    if (!valid(String.valueOf(i))) {
                        invalids.add(i);
                    }
                }
            }

            long sum = invalids.stream().mapToLong(Long::longValue).sum();

            if (test == sum)
                System.out.println("passed");
            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean valid(String id) {
        List<Integer> lengths = divisors(id.length()).stream().map(n -> id.length() / n).toList();

        for (Integer n : lengths) {
            List<String> l = splitEqually(id, n);
            if (l.stream().distinct().toArray().length == 1) {
                return false;
            }
        }

        return true;
    }

    private static List<Integer> divisors(int n) {
        List<Integer> ret = new ArrayList<Integer>();

        for (int i = n; i > 1; i--) {
            double div = n / (double) i;
            double mod = div % 1;
            if (mod == 0)
                ret.add(i);
        }
        return ret;
    }

    private static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<>();
        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
}