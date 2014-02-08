package com.asynchrony.webdriver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class Webdriver {

    String baseURL = "";
    WebDriver driver;

    @Before
    public void SetUpEachTest()
    {
        Setup.SetTestStartTime();
        driver = Setup.GetWebDriver();
    }

    @After
    public void TearDownAfterEachTest()
    {
        Log.Info("Test duration was: " + Setup.GetTestDurationInSeconds() + " seconds\n");
        driver.quit();
    }

    @Test
    public void FirstTest()
    {
        driver.navigate().to(baseURL);
        Log.Info("Page title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("mobile"));
        Log.WriteFinalLog();
    }

    Webdriver()
    {
        Properties logProperties = new Properties();
        InputStream input;

        try
        {
            input = new FileInputStream("config.properties");
            logProperties.load(input);
            baseURL = logProperties.getProperty("baseURL", "http://localhost");

        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
