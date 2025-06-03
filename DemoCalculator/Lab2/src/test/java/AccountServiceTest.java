package ducct.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử các chức năng của AccountService
 */
public class AccountServiceTest {

    private final AccountService accountService = new AccountService();

    /**
     * Test đăng ký tài khoản từ dữ liệu CSV (test-data.csv)
     * File nằm trong src/test/resources
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testRegisterAccount(String username, String password, String email, boolean expected) {
        boolean actual = accountService.registerAccount(username, password, email);

        // Ghi log kết quả kiểm thử (tùy chọn)
        UnitTestLogger.logResult(username, password, email, expected, actual);

        assertEquals(expected, actual, "Kiểm tra đăng ký tài khoản cho user: " + username);
    }

    /**
     * Test đăng ký 2 lần với cùng 1 username → lần 2 phải thất bại
     */
    @Test
    void testRegisterDuplicateUsername() {
        String username = "duplicateUser";
        String password1 = "Abcd123!";
        String password2 = "Xyz123!@";
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";

        boolean firstAttempt = accountService.registerAccount(username, password1, email1);
        boolean secondAttempt = accountService.registerAccount(username, password2, email2);

        assertTrue(firstAttempt, "Lần đầu đăng ký phải thành công");
        assertFalse(secondAttempt, "Đăng ký trùng username phải thất bại");
    }

    /**
     * Test kiểm tra mật khẩu mạnh
     */
    @Test
    void testIsStrongPassword() {
        assertTrue(accountService.isStrongPassword("Abcd123!"));   // hợp lệ
        assertFalse(accountService.isStrongPassword("weakpass"));  // thiếu số, ký tự đặc biệt
        assertFalse(accountService.isStrongPassword("NoNumber!")); // thiếu số
        assertFalse(accountService.isStrongPassword("nonumber1")); // thiếu chữ hoa và ký tự đặc biệt
        assertFalse(accountService.isStrongPassword("NOLOWER1!")); // thiếu chữ thường
    }

    /**
     * Test kiểm tra định dạng email hợp lệ
     */
    @Test
    void testIsValidEmail() {
        assertTrue(accountService.isValidEmail("user@example.com"));
        assertFalse(accountService.isValidEmail("userexample.com"));
        assertFalse(accountService.isValidEmail("user@.com"));
    }

    /**
     * Test email công ty (đuôi @company.com)
     */
    @Test
    void testIsCompanyEmail() {
        assertTrue(accountService.isCompanyEmail("user@company.com"));
        assertFalse(accountService.isCompanyEmail("user@gmail.com"));
    }

    /**
     * Test kiểm tra username đã tồn tại hay chưa
     */
    @Test
    void testIsUsernameTaken() {
        String username = "checkTaken";
        accountService.registerAccount(username, "Abcd123!", "check@example.com");
        assertTrue(accountService.isUsernameTaken(username));
        assertFalse(accountService.isUsernameTaken("notTaken"));
    }
}
