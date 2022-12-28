package mk.ukim.finki.wp.lab.selenium;


import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    private HtmlUnitDriver driver;


    @Before
    public void setup() {
        this.driver = new HtmlUnitDriver(true);
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }


    @Test
    public void testScenario() throws Exception {
        CoursesPage coursesPage = CoursesPage.to(this.driver);
        coursesPage.assertElements(0, 0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        coursesPage = LoginPage.doLogin(this.driver, loginPage, "nnikolovskii", "admin");
        coursesPage.assertElements(0, 0, 0, 0, 1);

        coursesPage = AddOrEditCourse.addCourse(this.driver, "test", "description1", "NN");
        coursesPage.assertElements(1, 1, 1, 1, 1);

        coursesPage = AddOrEditCourse.addCourse(this.driver, "test1", "description2", "SS");
        coursesPage.assertElements(2, 2, 2, 2, 1);

        coursesPage.getDeleteButtons().get(1).click();
        coursesPage.assertElements(1, 1, 1, 1, 1);

        coursesPage = AddOrEditCourse.editCourse(this.driver, coursesPage.getEditButtons().get(0), "test1", "200", "GG");
        coursesPage.assertElements(1, 1, 1, 1, 1);

    }

}
