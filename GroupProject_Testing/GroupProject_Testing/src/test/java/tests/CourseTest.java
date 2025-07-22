package tests;

import org.junit.jupiter.api.*;
import pages.CoursePage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Course Management Tests")
public class CourseTest extends BaseTest {
    private LoginPage loginPage;
    private CoursePage coursePage;

    @BeforeEach
    public void initPage() {
        loginPage = new LoginPage(driver);
        coursePage = new CoursePage(driver);
        loginPage.login("validUser", "validPass");  // ✅ Đảm bảo đã đăng nhập
    }

    @Test
    @Order(1)
    @DisplayName("Add a new course")
    public void testAddCourse() {
        String courseName = "Java Selenium " + System.currentTimeMillis();
        coursePage.addCourse(courseName);
        assertTrue(coursePage.isCourseAdded(courseName),
                "Expected course to be added and visible in the list.");
    }

    @Test
    @Order(2)
    @DisplayName("Edit an existing course")
    public void testEditCourse() {
        String oldName = "Course To Edit " + System.currentTimeMillis();
        String newName = "Edited Course " + System.currentTimeMillis();

        coursePage.addCourse(oldName); // Thêm trước để sửa
        coursePage.editCourse(oldName, newName);
        assertTrue(coursePage.isCourseDisplayed(newName),
                "Expected course name to be updated.");
    }

    @Test
    @Order(3)
    @DisplayName("Delete an existing course")
    public void testDeleteCourse() {
        String courseName = "Course To Delete " + System.currentTimeMillis();

        coursePage.addCourse(courseName); // Thêm trước để xóa
        coursePage.deleteCourse(courseName);
        assertFalse(coursePage.isCourseDisplayed(courseName),
                "Expected course to be deleted and no longer visible.");
    }
}
