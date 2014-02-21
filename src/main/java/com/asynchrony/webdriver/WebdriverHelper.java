package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.DriverSource;
import com.asynchrony.webdriver.wordpress.WordPressLoginPage;
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
    private int defaultPause = 1000;
    private int defaultWaitWhileSearching = 5000;

    public WebdriverHelper(DriverSource driverSource)
    {
        this.driverSource = driverSource;
    }

    public WebDriver getDriver() {
        return driverSource.getDriver();
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
        String screenshotPath = path + System.getProperty("file.separator") + "screenshot.png";
        try
        {
            File screenshot = ((TakesScreenshot) driverSource.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotPath));
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.info("Screenshot failed \n" + e.toString());
            Log.info("Attempted path was: " + screenshotPath);
        }
    }

    public String randomString(int stringLength)
    {
        return RandomStringUtils.random(stringLength);
    }

    public void clickAndWait(WebElement element)
    {
        element.click();
        pause(defaultPause);
    }

    public String getElementValue(WebElement element)
    {
        return getElementAttribute(element, "value");
    }

    public String getElementCssClass(WebElement element)
    {
        return getElementAttribute(element, "class");
    }

    public String getElementAttribute(WebElement element, String attribute)
    {
        return element.getAttribute(attribute);
    }

    public WebElement getWebElementSingle(By selector)
    {
        try
        {
            return waitUntilFound(driverSource, selector);
        } catch (WebDriverException ex)
        {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    public List<WebElement> getWebElementList(By selector)
    {
        try
        {
            return waitUntilListFound(driverSource, selector);
        } catch (WebDriverException ex)
        {
            Log.error("Timeout on single: " + selector);
        }
        return null;
    }

    private WebElement waitUntilFound(DriverSource driverSource, By locator)
    {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), defaultWaitWhileSearching);
        //waiter.ignoring(NoSuchElementException.class);
        return waiter.until(presenceOfElementLocated(locator));
    }

    private List<WebElement> waitUntilListFound(DriverSource driverSource, By locator)
    {
        WebDriverWait waiter = new WebDriverWait(driverSource.getDriver(), defaultWaitWhileSearching);
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
        // drag and drop is flaky, caveat emptor
        Actions performDragAndDrop = new Actions(driverSource.getDriver());
        Action dragAndDrop = performDragAndDrop.dragAndDrop(source, target).build();
        dragAndDrop.perform();
    }

    public void pause(int snoozeLength)
    {
        if (snoozeLength == 0)
            snoozeLength = defaultPause;

        try
        {
            Thread.sleep(snoozeLength);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
