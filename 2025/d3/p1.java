package d3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class p1 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("2025/d3/input.txt"));

            List<String> l = new ArrayList<String>();

            while (in.hasNext()) {
                l.add(in.nextLine());
            }

            in.close();

            System.out.println(l.stream().map(it -> largest(it)).mapToLong(Long::valueOf).sum());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String largest(String s) {
        char[] ret = new char[2];
        ret[0] = s.charAt(0);

        for (char c : s.substring(0, s.length() - 1).toCharArray()) {
            if (c > ret[0]) {
                ret[0] = c;
            }
        }

        ret[1] = s.charAt(s.indexOf(ret[0]) + 1);

        for (char c : s.substring(s.indexOf(ret[0]) + 1).toCharArray()) {
            if (c > ret[1]) {
                ret[1] = c;
            }
        }

        return String.valueOf(ret);
    }
}