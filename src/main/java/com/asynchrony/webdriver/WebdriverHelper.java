package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.DriverSource;

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
}
