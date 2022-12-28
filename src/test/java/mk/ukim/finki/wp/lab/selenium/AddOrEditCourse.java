package mk.ukim.finki.wp.lab.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditCourse extends AbstractPage{
    @FindBy(id = "name")
    private WebElement name;
    private WebElement description;
    private WebElement dropdown;
    private WebElement submit;

    public AddOrEditCourse(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage addCourse(WebDriver driver, String name, String description, String teacher) {
        get(driver, "/courses/add-course");
        AddOrEditCourse addOrEditProduct = PageFactory.initElements(driver, AddOrEditCourse.class);
        addOrEditProduct.name.sendKeys(name);
        addOrEditProduct.description.sendKeys(description);
        addOrEditProduct.dropdown.click();
        addOrEditProduct.dropdown.sendKeys(teacher);
        addOrEditProduct.dropdown.findElement(By.xpath("//option[. = '" + teacher + "']")).click();

        addOrEditProduct.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public static CoursesPage editCourse(WebDriver driver, WebElement editButton, String name, String description, String teacher) {
        editButton.click();
        System.out.println(driver.getCurrentUrl());
        AddOrEditCourse addOrEditProduct = PageFactory.initElements(driver, AddOrEditCourse.class);
        addOrEditProduct.name.sendKeys(name);
        addOrEditProduct.description.sendKeys(description);
        addOrEditProduct.dropdown.click();
        addOrEditProduct.dropdown.findElement(By.xpath("//option[. = '" + teacher + "']")).click();

        addOrEditProduct.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }

}
