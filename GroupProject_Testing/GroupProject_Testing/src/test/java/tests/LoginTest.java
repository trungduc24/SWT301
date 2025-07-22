package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Login Functionality Tests")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Valid login should redirect to dashboard")
    public void testValidLogin() {
        loginPage.login("validUser", "validPass");
        assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "Expected to be redirected to dashboard after valid login");
    }

    @Test
    @Order(2)
    @DisplayName("Invalid login should show error message")
    public void testInvalidLogin() {
        loginPage.login("invalidUser", "invalidPass");
        String errorMsg = loginPage.getErrorMessage();
        assertEquals("Invalid credentials", errorMsg);
    }

    @ParameterizedTest
    @Order(3)
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    @DisplayName("Login test with CSV data")
    public void testLoginFromCsv(String username, String password, String expectedResult) {
        // Xử lý đặc biệt cho emptyUser / emptyPass
        if (username.equalsIgnoreCase("emptyUser")) username = "";
        if (password.equalsIgnoreCase("emptyPass")) password = "";

        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("success")) {
            assertTrue(driver.getCurrentUrl().contains("dashboard"),
                    "Expected successful login but dashboard not reached.");
        } else {
            String errorMsg = loginPage.getErrorMessage();
            assertNotNull(errorMsg);
            assertFalse(errorMsg.isEmpty(), "Expected error message but none was displayed.");
        }
    }
}
