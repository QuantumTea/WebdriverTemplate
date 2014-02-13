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
public class ExampleTest
{
    WebDriver driver;
    WebdriverHelper helper;

    @InjectProperty("baseURL")
    String baseUrl;

    @InjectProperty("defaultSleep")
    int defaultSleep;
    // the above line doesn't inject the property

    @Before
    public void setUp()
    {
    }

    @Test
    public void googleSearchTest() throws Exception
    {
        helper.navigateTo(baseUrl);
        WebElement searchInput = helper.getWebElementSingle(By.name("q"));

        searchInput.sendKeys("cheese");
        searchInput.submit();
        helper.pause(defaultSleep);

        helper.assertTitleContains("cheese");
    }

    @Test
    @Config("SecondExampleTestData.properties")
    public void testOtherThing() throws Exception
    {
        helper.navigateTo(baseUrl);
        assertTrue(true);
    }

    @Test
    @Driver(ChromeDriver.class)
    public void testSecondThing() throws Exception
    {
        helper.navigateTo(baseUrl);
        helper.pause(defaultSleep);
        System.out.println("Sleep: " + defaultSleep);
        assertFalse(false);
    }
}
