package tests;

import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Profile Upload Image Tests")
public class ProfileTest extends BaseTest {
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @BeforeEach
    public void initPage() {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // Đăng nhập trước khi upload ảnh
        loginPage.login("validUser", "validPass");
    }

    @Test
    @Order(1)
    @DisplayName("Upload a valid profile image")
    public void testUploadImage() {
        // Đường dẫn ảnh mẫu trong thư mục resources
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/sample-image.jpg";
        profilePage.uploadImage(imagePath);

        assertTrue(profilePage.isImageUploaded(),
                "Expected profile image to be uploaded and visible.");
    }
}
