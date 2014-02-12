package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.DriverSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

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

    public void takeScreenshot(DriverSource driverSource, String path) {
        path = path + System.getProperty("file.separator") + "screenshot.png";
        try {
            File screenshot = ((TakesScreenshot) driverSource.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            Log.info("Screenshot failed \n" + e.toString());
        }
    }

    public String randomString(int stringLength) {
        return RandomStringUtils.random(stringLength);
    }

    public void clickWait(WebElement element) {
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getElementValue(WebElement element) {
        return element.getAttribute("value");
    }

    public String getElementCssClass(WebElement element) {
        return element.getAttribute("class");
    }

    public WebElement getWebElementSingleBy(DriverSource driverSource, By selector) {
        // add config for the wait timeout, reference it here
        int timeoutOnWait = 3000;
        try {
            return waitUntilFound(driverSource, selector, timeoutOnWait);
        } catch (WebDriverException ex) {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    public List<WebElement> getWebElementListBy(DriverSource driverSource, By selector) {
        // add config for the wait timeout, reference it here
        int timeoutOnWait = 3000;

        try {
            return waitUntilListFound(driverSource, selector, timeoutOnWait);
        } catch (WebDriverException ex) {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    public WebElement waitUntilFound(DriverSource driverSource, By locator, int timeout) {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), timeout);
        waiter.ignoring(NoSuchElementException.class);
        return waiter.until(presenceOfElementLocated(locator));
    }

    public List<WebElement> waitUntilListFound(DriverSource driverSource, By locator, int timeout) {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), timeout);
        waiter.ignoring(NoSuchElementException.class);
        return waiter.until(presenceOfAllElementsLocatedBy(locator));
    }
}
