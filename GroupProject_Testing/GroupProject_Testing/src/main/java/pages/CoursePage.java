package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CoursePage extends BasePage {

    // Locators
    private By addCourseBtn = By.id("addCourse");
    private By courseNameInput = By.id("courseName");
    private By saveBtn = By.id("saveCourse");
    private By courseListItems = By.cssSelector(".course-item"); // mỗi khóa học có class course-item
    private By successMsg = By.id("successMsg");

    // Dynamic locators (sử dụng trong edit/delete)
    private String courseItemXpath = "//div[contains(@class, 'course-item') and contains(., '%s')]";
    private String editBtnXpath = courseItemXpath + "//button[contains(@class,'edit')]";
    private String deleteBtnXpath = courseItemXpath + "//button[contains(@class,'delete')]";

    public CoursePage(WebDriver driver) {
        super(driver);
    }

    /** Add new course */
    public void addCourse(String courseName) {
        click(addCourseBtn);
        type(courseNameInput, courseName);
        click(saveBtn);
    }

    /** Check if course was added */
    public boolean isCourseAdded(String courseName) {
        return isCourseDisplayed(courseName);
    }

    /** Check if success message is displayed */
    public boolean isSuccessMessageDisplayed() {
        try {
            waitForVisibility(successMsg);
            return driver.findElement(successMsg).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Edit a course by name */
    public void editCourse(String oldName, String newName) {
        By editBtn = By.xpath(String.format(editBtnXpath, oldName));
        click(editBtn);

        // Update course name
        type(courseNameInput, newName);
        click(saveBtn);
    }

    /** Delete a course by name */
    public void deleteCourse(String courseName) {
        By deleteBtn = By.xpath(String.format(deleteBtnXpath, courseName));
        click(deleteBtn);
        driver.switchTo().alert().accept(); // nếu có alert xác nhận
    }

    /** Check if course is displayed in the list */
    public boolean isCourseDisplayed(String courseName) {
        waitForVisibility(By.cssSelector(".course-list")); // container chính
        List<WebElement> courses = driver.findElements(courseListItems);
        for (WebElement course : courses) {
            if (course.getText().contains(courseName)) {
                return true;
            }
        }
        return false;
    }
}
