package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.DriverSource;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class WebdriverHelper {
    private DriverSource driverSource;

    public WebdriverHelper(DriverSource driverSource) {
        this.driverSource = driverSource;
    }

    public void navigateTo(String url) {
        driverSource.getDriver().navigate().to(url);
    }

    public void assertTitleContains(String value) {
        assertTrue(driverSource.getDriver().getTitle().contains(value));
    }

    public void takeScreenshot(DriverSource driverSource, String path)
    {
        path = path + System.getProperty("file.separator") + "screenshot.png";
        try {
            File screenshot = ((TakesScreenshot) driverSource.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            Log.info("Screenshot failed \n" + e.toString());
        }
    }

    public String randomString(int stringLength)
    {
        String randomCharacters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        do
        {
            // add random character from string
        } while (sb.length() < stringLength);

        return sb.toString();
    }

    public void clickWait(WebElement element)
    {
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getElementValue(WebElement element)
    {
        return element.getAttribute("value");
    }
}
