package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://yourwebsite.com");  // URL dự án
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
