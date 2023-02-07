package console.util;

public final class ConsoleWriter{
    public static void write(String message) {
        System.out.println(message);
    }

    public static void writeError(String message) {
        System.err.println(message);
    }

}
