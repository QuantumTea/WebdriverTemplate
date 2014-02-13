package com.asynchrony.webdriver;

import com.asynchrony.webdriver.annotations.Config;
import com.asynchrony.webdriver.annotations.Driver;
import com.asynchrony.webdriver.annotations.InjectProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(WebdriverTestRunner.class)
@Config("ExampleTestData.properties")
@Driver(FirefoxDriver.class)
public class ExampleTest {
    WebDriver driver;
    WebdriverHelper helper;

    @InjectProperty("baseURL")
    String baseUrl;

    @Before
    public void setUp() {
    }

    @Test
    public void googleSearchTest() throws Exception {
        helper.navigateTo(baseUrl);
        WebElement searchInput = helper.getWebElementSingle(By.name("q"));

        searchInput.sendKeys("cheese");
        searchInput.submit();
        Thread.sleep(1000);

        helper.assertTitleContains("cheese");
    }

    @Test
    @Config("SecondExampleTestData.properties")
    public void testOtherThing() throws Exception {
        Thread.sleep(100);
        assertTrue(true);
    }

    @Test
    @Driver(ChromeDriver.class)
    public void testSecondThing() throws Exception {
        Thread.sleep(1500);
        assertFalse(!true);
    }
}
