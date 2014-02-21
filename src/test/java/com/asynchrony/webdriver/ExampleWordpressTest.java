package com.asynchrony.webdriver;

import com.asynchrony.webdriver.annotations.Driver;
import com.asynchrony.webdriver.wordpress.WordpressTestRunner;
import com.asynchrony.webdriver.wordpress.annotations.WordpressLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

@RunWith(WordpressTestRunner.class)
@Driver(FirefoxDriver.class)
public class ExampleWordpressTest {
    public static final String VAGRANTPRESS_URL = "http://localhost:8080/";

    private WebDriver driver;
    private WebdriverHelper helper;

    private String theUrl = VAGRANTPRESS_URL;

    @Test
    public void testSiteIsAccessible() throws Exception {
        helper.navigateTo(VAGRANTPRESS_URL);
    }

    @Test
    @WordpressLogin(username = "admin", password = "vagrant", url = VAGRANTPRESS_URL)
    public void testLoggingIntoWordpressUsingAnnotationUrl() throws Exception {
        assertEquals(
                VAGRANTPRESS_URL + "wp-admin/",
                driver.getCurrentUrl());
    }

    @Test
    @WordpressLogin(username = "admin", password = "vagrant", urlVariable = "theUrl")
    public void testLoggingIntoWordpressUsingTestBasedData() throws Exception {
        assertEquals(
                VAGRANTPRESS_URL + "wp-admin/",
                driver.getCurrentUrl());
    }
}
