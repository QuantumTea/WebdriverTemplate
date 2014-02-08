package com.asynchrony.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Date;

public class Setup {

    private static double singleTestStartTime = 0;

    public static void SetTestStartTime()
    {
        singleTestStartTime = new Date().getTime();
    }

    public static double GetTestDurationInSeconds()
    {
        return (System.currentTimeMillis() - singleTestStartTime) / 1000;
    }

    public static WebDriver GetWebDriver()
    {
        return new FirefoxDriver();
    }
}
