package d1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class p2 {
    public static void main(String[] args) {
        // Read input from file
        try {
            File file = new File("2025/d1/input.txt");
            Scanner scanner = new Scanner(file);
            List<Integer> numbers = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    int parity = line.charAt(0) == 'L' ? -1 : 1;
                    numbers.add(parity * Integer.parseInt(line.substring(1)));
                }
            }

            scanner.close();

            int position = 50;

            int zeroCount = 0;

            for (Integer num : numbers) {
                for (int i = 0; i < Math.abs(num); i++) {
                    position = wrap(position + (num < 0 ? -1 : 1));
                    if (position == 0) {
                        zeroCount++;
                    }
                }
            }
            System.out.println("Zero count: " + zeroCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int wrap(int value) {
        int result = value % 100;
        return result < 0 ? result + 100 : result;
    }
}