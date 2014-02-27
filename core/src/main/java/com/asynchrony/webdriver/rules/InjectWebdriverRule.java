package com.asynchrony.webdriver.rules;

import com.asynchrony.webdriver.Log;
import com.asynchrony.webdriver.annotations.Driver;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.lang.reflect.Field;

public class InjectWebdriverRule implements MethodRule, DriverSource
{
    private WebDriver driver;

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                try
                {
                    injectWebdriver(method, target);
                    base.evaluate();
                } finally
                {
                    shutdownDriver();
                }
            }
        };
    }

    public WebDriver getDriver()
    {
        return driver;
    }

    private void shutdownDriver()
    {
        if (driver != null)
        {
            driver.quit();
            driver = null;
        }
    }

    private void injectWebdriver(FrameworkMethod method, Object target)
    {
        Class<?> klass = target.getClass();
        Class<? extends WebDriver> driverClass = getDriver(klass, method);

        try
        {
            WebDriver webDriver = driverClass.newInstance();
            driver = webDriver;

            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields)
            {
                if (field.getType().isAssignableFrom(driverClass))
                {
                    field.setAccessible(true);
                    field.set(target, driver);
                }
            }
        } catch (Exception e)
        {
            shutdownDriver();
        }
    }

    private Class<? extends WebDriver> getDriver(Class<?> klass, FrameworkMethod method)
    {
        return new AnnotationValueExtractor<Driver, Class<? extends WebDriver>>(Driver.class).value(klass, method);
    }
}
