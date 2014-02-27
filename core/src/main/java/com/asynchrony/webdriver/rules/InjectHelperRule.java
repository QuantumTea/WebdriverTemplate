package com.asynchrony.webdriver.rules;

import com.asynchrony.webdriver.WebdriverHelper;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;

public class InjectHelperRule implements MethodRule
{
    private DriverSource driver;

    public InjectHelperRule(DriverSource driver)
    {
        this.driver = driver;
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target)
    {
        for (Field field : target.getClass().getDeclaredFields())
        {
            if (field.getType().isAssignableFrom(WebdriverHelper.class))
            {
                try
                {
                    field.setAccessible(true);
                    field.set(target, new WebdriverHelper(driver));
                } catch (IllegalAccessException ignored)
                {
                }
            }
        }
        return base;
    }
}
