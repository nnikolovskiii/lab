package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        driver.get(url);
        System.out.println(driver.getCurrentUrl());
    }

}

