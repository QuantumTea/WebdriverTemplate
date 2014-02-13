package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.DriverSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class WebdriverHelper
{
    private DriverSource driverSource;
    private int defaultTimeout = 3000;

    public WebdriverHelper(DriverSource driverSource)
    {
        this.driverSource = driverSource;
    }

    public void navigateTo(String url)
    {
        driverSource.getDriver().navigate().to(url);
    }

    public void assertTitleContains(String value)
    {
        assertTrue(driverSource.getDriver().getTitle().toLowerCase()
                .contains(value.toLowerCase()));
    }

    public void assertElementTextContains(WebElement element, String value)
    {
        assertTrue(element.getText().toLowerCase()
                .contains(value.toLowerCase()));
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
        return RandomStringUtils.random(stringLength);
    }

    public void clickWait(WebElement element, int wait)
    {
        element.click();
        pause(wait);
    }

    public String getElementValue(WebElement element)
    {
        return element.getAttribute("value");
    }

    public String getElementCssClass(WebElement element)
    {
        return element.getAttribute("class");
    }

    public WebElement getWebElementSingle(By selector)
    {
        try {
            return waitUntilFound(driverSource, selector);
        } catch (WebDriverException ex) {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    public List<WebElement> getWebElementList(By selector)
    {
        try {
            return waitUntilListFound(driverSource, selector);
        } catch (WebDriverException ex) {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    private WebElement waitUntilFound(DriverSource driverSource, By locator)
    {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), defaultTimeout);
        //waiter.ignoring(NoSuchElementException.class);
        return waiter.until(presenceOfElementLocated(locator));
    }

    private List<WebElement> waitUntilListFound(DriverSource driverSource, By locator)
    {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), defaultTimeout);
        //waiter.ignoring(NoSuchElementException.class);
        return waiter.until(presenceOfAllElementsLocatedBy(locator));
    }

    public void hoverOverWebElement(WebElement target)
    {
        Actions performHover = new Actions(driverSource.getDriver());
        Action hoverMouse = performHover.moveToElement(target).build();
        hoverMouse.perform();
    }

    public void dragElementToOtherElement(WebElement source, WebElement target)
    {
        Actions performDragAndDrop = new Actions(driverSource.getDriver());
        Action dragAndDrop = performDragAndDrop.clickAndHold(source)
                .moveToElement(target)
                .release(target)
                .build();
        dragAndDrop.perform();
    }

    public void pause(int snoozeLength)
    {
        try {
            Thread.sleep(snoozeLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
