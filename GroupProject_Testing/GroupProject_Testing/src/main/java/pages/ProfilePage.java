package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
    private By uploadInput = By.id("uploadPhoto"); // input[type='file']
    private By uploadButton = By.id("uploadBtn");
    private By uploadedImage = By.id("uploadedImage"); // img hiện ảnh

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void uploadImage(String imagePath) {
        waitForVisibility(uploadInput);
        driver.findElement(uploadInput).sendKeys(imagePath);
        click(uploadButton);
    }

    public boolean isImageUploaded() {
        try {
            waitForVisibility(uploadedImage);
            return driver.findElement(uploadedImage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
