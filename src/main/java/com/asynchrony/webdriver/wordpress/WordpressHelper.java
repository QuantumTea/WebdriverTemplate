package com.asynchrony.webdriver.wordpress;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.asynchrony.webdriver.wordpress.WordPressLoginPage.*;

public class WordPressHelper
{
    private WebDriver driver;
    private String siteUrl;

    public WordPressHelper(WebDriver webDriver, String siteUrl)
    {
        this.siteUrl = siteUrl;
        this.driver = webDriver;
    }

    public void login(String username, String password) {
        driver.navigate().to(getLoginPageUrl(siteUrl));
        WebElement usernameInput = driver.findElement(By.id(getUsernameFieldId()));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id(getPasswordFieldId()));
        passwordInput.sendKeys(password);

        WebElement submitButton = driver.findElement(By.id(getSubmitButtonId()));
        submitButton.submit();
    }

}
