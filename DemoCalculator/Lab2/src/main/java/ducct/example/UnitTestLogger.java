package ducct.example;

import java.io.FileWriter;
import java.io.IOException;

public class UnitTestLogger {
    public static void logResult(String username, String password, String email, boolean expected, boolean actual) {
        try (FileWriter writer = new FileWriter("UnitTest.csv", true)) {
            writer.write(String.format("%s,%s,%s,%s,%s\n", username, password, email, expected, actual));
        } catch (IOException e) {
            System.err.println("Lỗi ghi file log: " + e.getMessage());
        }
    }
}
