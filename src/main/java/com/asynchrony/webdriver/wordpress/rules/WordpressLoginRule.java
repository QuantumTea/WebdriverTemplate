package com.asynchrony.webdriver.wordpress.rules;

import com.asynchrony.webdriver.rules.AnnotationValueExtractor;
import com.asynchrony.webdriver.wordpress.WordpressHelper;
import com.asynchrony.webdriver.wordpress.annotations.WordpressLogin;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;

/**
 * Created by dev1 on 2/20/14.
 */
public class WordpressLoginRule implements MethodRule {

    @Override
    public Statement apply(final Statement base, final FrameworkMethod frameworkMethod, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                AnnotationValueExtractor<WordpressLogin, String> extractor = new AnnotationValueExtractor<WordpressLogin, String>(WordpressLogin.class);
                WordpressLogin annotation = extractor.getAnnotation(target.getClass(), frameworkMethod);
                if (annotation != null) {
                    try {
                        Field baseUrlField = getBaseUrl(target);
                        if (baseUrlField != null) {
                            baseUrlField.setAccessible(true);
                            String baseUrl = (String) baseUrlField.get(target);
                            WordpressHelper helper = new WordpressHelper(getWebDriver(target), baseUrl);
                            helper.login(annotation.username(), annotation.password());
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
                base.evaluate();
            }
        };
    }

    private WebDriver getWebDriver(Object target) throws NoSuchFieldException, IllegalAccessException {
        Field driver = target.getClass().getDeclaredField("driver");
        driver.setAccessible(true);
        return (WebDriver) driver.get(target);
    }

    private Field getBaseUrl(Object target) {
        Field baseUrl = null;
        try {
            baseUrl = target.getClass().getDeclaredField("baseUrl");
        } catch (NoSuchFieldException e) {
            try {
                baseUrl = target.getClass().getDeclaredField("baseURL");
            } catch (NoSuchFieldException e1) {
            }
        }
        return baseUrl;
    }
}
