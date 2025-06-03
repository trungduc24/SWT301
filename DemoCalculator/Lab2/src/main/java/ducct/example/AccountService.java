package ducct.example;

import java.util.HashSet;
import java.util.Set;

/**
 * Lớp AccountService chứa các phương thức để kiểm tra và đăng ký tài khoản người dùng.
 */
public class AccountService {

    // Danh sách lưu các username đã đăng ký
    private Set<String> registeredUsernames = new HashSet<>();

    /**
     * Kiểm tra định dạng email hợp lệ.
     * Ví dụ hợp lệ: user@example.com
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Kiểm tra mật khẩu mạnh:
     * - Tối thiểu 8 ký tự
     * - Chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt
     */
    public boolean isStrongPassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    /**
     * Kiểm tra username đã được đăng ký chưa.
     */
    public boolean isUsernameTaken(String username) {
        return registeredUsernames.contains(username);
    }

    /**
     * Kiểm tra email thuộc công ty (kết thúc bằng @company.com).
     */
    public boolean isCompanyEmail(String email) {
        return email != null && email.endsWith("@company.com");
    }

    /**
     * Phương thức đăng ký tài khoản với các điều kiện:
     * - Username không rỗng và chưa tồn tại
     * - Password mạnh
     * - Email hợp lệ
     */
    public boolean registerAccount(String username, String password, String email) {
        if (username == null || username.isEmpty()) return false;
        if (isUsernameTaken(username)) return false;
        if (!isStrongPassword(password)) return false;
        if (!isValidEmail(email)) return false;

        registeredUsernames.add(username);
        return true;
    }
}
