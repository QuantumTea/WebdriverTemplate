package com.asynchrony.webdriver.wordpress;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static com.asynchrony.webdriver.wordpress.WordPressLoginPage.*;

public class WordpressHelper
{
    private WebDriver driver;
    private String siteUrl;

    public WordpressHelper(WebDriver webDriver, String siteUrl)
    {
        this.siteUrl = siteUrl;
        this.driver = webDriver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

    public void gotoTrash() {
        driver.navigate().to(siteUrl + "/wp-admin/edit.php?post_status=trash&post_type=page");
    }

    public void emptyTrash() {
        gotoTrash();
        driver.findElement(By.id("delete_all")).click();
    }

    public WordpressPage createWordpressPage(String pageTitle, String pageContent) {
        driver.navigate().to(siteUrl + "/wp-admin/post-new.php?post_type=page");
        WebElement title = driver.findElement(By.id("title"));
        title.sendKeys(pageTitle);
        enterContent(pageContent);

        WebElement publish = driver.findElement(By.cssSelector("input[id='publish'][type='submit']"));
        publish.click();

        WebElement message = driver.findElement(By.id("message"));
        WebElement link = message.findElement(By.linkText("View page"));
        String submitdeleteUrl = driver.findElement(By.cssSelector("a[class='submitdelete deletion']")).getAttribute("href");
        String viewUrl = link.getAttribute("href");
        return new WordpressPage(viewUrl, submitdeleteUrl);
    }

    private void enterContent(String pageContent) {
        WebElement tinymceEditor = null;
        WebElement textEditor = null;
        try {
            driver.switchTo().frame(driver.findElement(By.id("content_ifr")));
            tinymceEditor = driver.findElement(By.id("tinymce"));
        } catch (Exception e) {
            textEditor = driver.findElement(By.id("content"));
        }
        WebElement editor = tinymceEditor != null ? tinymceEditor : textEditor;
        editor.sendKeys(pageContent);
        if (tinymceEditor != null) {
            driver.switchTo().defaultContent();
        }
    }
}
