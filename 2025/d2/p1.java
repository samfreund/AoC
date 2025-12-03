package d2;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class p1 {
    public static void main(String[] args) {
        int test = 1227775554;

        try {
            Scanner in = new Scanner(new File("2025/d2/input.txt"));

            String line = in.nextLine();
            in.close();

            List<Long> invalids = new LinkedList<>();

            for (String s : line.split(",")) {
                String[] split = s.split("-");

                for (Long i = Long.parseLong(split[0]); i <= Long.parseLong(split[1]); i++) {
                    if (invalid(String.valueOf(i))) {
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

    private static boolean invalid(String id) {
        if (id.length() % 2 == 1) {
            return false;
        }

        String first = id.substring(0, (id.length() / 2));
        String second = id.substring((id.length() / 2));

        return first.equals(second);
    }
}