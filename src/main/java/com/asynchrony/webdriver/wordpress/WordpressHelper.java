package com.asynchrony.webdriver.wordpress;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WordpressHelper {
    private WebDriver driver;
    private String baseUrl;

    public WordpressHelper(WebDriver webDriver, String baseUrl) {
        this.baseUrl = baseUrl;
        driver = webDriver;
    }

    public void login(String username, String password) {
        driver.navigate().to(baseUrl+"/wp-login.php");
        WebElement usernameInput = driver.findElement(By.id("user_login"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("user_pass"));
        passwordInput.sendKeys(password);

        WebElement submitButton = driver.findElement(By.id("wp-submit"));
        submitButton.submit();
    }
}
