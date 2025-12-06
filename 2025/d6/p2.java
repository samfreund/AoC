package d6;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class p2 {
    public static void main(String[] args) throws Exception {
        File f = new File("2025/d6/input.txt");
        long lines = Files.lines(f.toPath()).count();
        Scanner in = new Scanner(f);

        // This is the input format, row x col
        List<String> l = new ArrayList<String>();

        for (int i = 0; i < lines - 1; i++) {
            l.add(in.nextLine());
        }

        List<List<Character>> m = new ArrayList<>();

        // We assume all the input strings are the same size
        for (int i = 0; i < l.get(0).length(); i++) {
            List<Character> c_l = new LinkedList<>();

            for (String s : l) {
                c_l.add(s.charAt(i));
            }

            m.add(c_l);
        }

        List<List<List<Character>>> groups = new LinkedList<>();

        int prior = 0;

        for (int i = 0; i < m.size(); i++) {
            if (m.get(i).stream().allMatch(c -> c == ' ')) {
                groups.add(m.subList(prior, i));
                prior = i + 1;
            } else if (i == m.size() - 1) {
                groups.add(m.subList(prior, i + 1));
            }

        }

        long total = 0;

        for (List<List<Character>> group : groups) {
            Boolean op = in.next("\\*|\\+").matches("\\*");
            List<Integer> nums = new LinkedList<Integer>();

            for (List<Character> num : group) {
                nums.add(Integer.valueOf(
                        num.stream().filter(c -> c != ' ').map(String::valueOf).collect(Collectors.joining())));
            }

            LongStream longs = nums.stream().mapToLong(Long::valueOf);
            long result = op ? longs.reduce(1, (a, b) -> a * b) : longs.sum();
            total += result;

        }

        in.close();

        System.out.println(total);
    }
}