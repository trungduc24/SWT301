package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Register Form Automation Test")
public class RegisterTest extends BaseTest {

    @Test
    @DisplayName("Should register successfully with valid data")
    void testRegisterForm() throws InterruptedException {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigate();

        // Lấy đường dẫn tuyệt đối của ảnh
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/testimage.png";

        // Điền form
        registerPage.fillForm(
                "Nguyen",
                "Van A",
                "nva@example.com",
                "0912345678",
                imagePath
        );

        // --- Fix lỗi bị iframe quảng cáo che Submit ---

        // 1️⃣ Cuộn đến nút Submit để tránh bị che:
        WebElement submitBtn = registerPage.getSubmitButtonElement();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        Thread.sleep(300); // Đợi scroll xong, tránh click khi trang chưa ổn định

        // 2️⃣ Dùng JavaScript click để bypass iframe che:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        // -------------------------------------------

        // Kiểm tra modal xác nhận hiển thị
        assertTrue(registerPage.isConfirmationDisplayed());
    }
}
