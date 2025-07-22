package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By genderMaleRadio = By.xpath("//label[text()='Male']");
    private final By mobileField = By.id("userNumber");
    private final By uploadPictureButton = By.id("uploadPicture");
    private final By submitButton = By.id("submit");
    private final By confirmationModal = By.id("example-modal-sizes-title-lg");

    // Actions
    public void navigate() {
        driver.get("https://demoqa.com/automation-practice-form");
    }

    public void fillForm(String firstName, String lastName, String email, String mobile, String imagePath) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        click(genderMaleRadio);
        type(mobileField, mobile);
        driver.findElement(uploadPictureButton).sendKeys(imagePath);
    }

    public boolean isConfirmationDisplayed() {
        return waitForVisibility(confirmationModal).isDisplayed();
    }

    public WebElement getSubmitButtonElement() {
        return driver.findElement(submitButton);
    }
}
