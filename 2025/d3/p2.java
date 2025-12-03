package d3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class p2 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("2025/d3/input.txt"));

            List<String> l = new ArrayList<String>();

            while (in.hasNext()) {
                l.add(in.nextLine());
            }

            in.close();

            System.out.println(l.stream().map(it -> largest(it, 12)).mapToLong(Long::valueOf).sum());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String largest(String s, int length) {

        if (s.length() == length) {
            return s;
        }

        char f = s.charAt(0);

        for (char c : s.substring(0, s.length() - length + 1).toCharArray()) {
            if (c > f) {
                f = c;
            }
        }

        return length != 1 ? f + largest(s.substring(s.indexOf(f) + 1), length - 1) : String.valueOf(f);
    }
}