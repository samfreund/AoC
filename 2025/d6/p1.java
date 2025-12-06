package d6;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.LongStream;

class p1 {
    public static void main(String[] args) throws Exception {
        File f = new File("2025/d6/input.txt");
        long lines = Files.lines(f.toPath()).count();
        Scanner in = new Scanner(f);

        // This is the input format, row x col
        List<List<Integer>> m_in = new ArrayList<List<Integer>>();

        for (int i = 0; i < lines - 1; i++) {
            List<String> l = List.of(in.nextLine().split(" +"));

            m_in.add(l.stream().filter(it -> it.matches("\\d+")).mapToInt(Integer::valueOf).boxed().toList());
        }

        long total = 0;

        for (int i = 0; i < m_in.get(0).size(); i++) {
            List<Integer> row = new LinkedList<Integer>();

            for (List<Integer> row_old : m_in) {
                row.add(row_old.get(i));
            }
            Boolean op = in.next("\\*|\\+").matches("\\*");

            LongStream nums = row.stream().mapToLong(Long::valueOf);
            long result = op ? nums.reduce(1, (a, b) -> a * b) : nums.sum();
            total += result;

        }

        in.close();

        System.out.println(total);
    }
}