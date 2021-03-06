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
import org.openqa.selenium.ie.InternetExplorerDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(WebdriverTestRunner.class)
@Config("ExampleTestData.properties")
@Driver(ChromeDriver.class)
public class ExampleTest
{
    WebDriver driver;
    WebdriverHelper helper;

    @InjectProperty("baseURL")
    String baseUrl;

    @InjectProperty("defaultSleep")
    String defaultSleepStringValue;

    int defaultSleep;

    @Before
    public void setUp()
    {
        defaultSleep = Integer.parseInt(defaultSleepStringValue);
    }

    @Test
    public void googleSearchTest() throws Exception
    {
        helper.navigateTo(baseUrl);
        WebElement searchInput = helper.getWebElementSingle(By.name("q"));

        searchInput.sendKeys("cheese");
        searchInput.submit();
        helper.pause(defaultSleep);

        System.out.println("Sleep: " + defaultSleep);
        helper.assertTitleContains("cheese");
    }

    @Test
    @Config("SecondExampleTestData.properties")
    // @Driver(InternetExplorerDriver.class)
    public void testOtherThing() throws Exception
    {
        helper.navigateTo(baseUrl);
        assertTrue(true);
    }

    @Test
    // @Driver(ChromeDriver.class)
    public void testSecondThing() throws Exception
    {
        helper.navigateTo(baseUrl);
        assertFalse(false);
    }
}
