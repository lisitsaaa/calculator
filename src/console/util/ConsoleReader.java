package console.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleReader{
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readString() {
        return SCANNER.next();
    }

    public static int readInteger() {
        return SCANNER.nextInt();
    }

    public static List<Double> readDoubleList() {
        Scanner scanner = new Scanner(System.in);
        List<Double> numbers = new ArrayList<>();

        String s = scanner.nextLine();
        String[] s1 = s.split(" ");

        for (String value : s1) {
            double tmp = Double.parseDouble(value);
            numbers.add(tmp);
        }
        return numbers;
    }

}
