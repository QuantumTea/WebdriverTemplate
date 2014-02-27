package com.asynchrony.webdriver.wordpress.rules;

import com.asynchrony.webdriver.rules.AnnotationValueExtractor;
import com.asynchrony.webdriver.rules.DriverSource;
import com.asynchrony.webdriver.wordpress.WordpressHelper;
import com.asynchrony.webdriver.wordpress.annotations.WordpressLogin;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;

/**
 * Created by dev1 on 2/20/14.
 */
public class WordpressLoginRule implements MethodRule {

    private DriverSource driverSource;

    public WordpressLoginRule(DriverSource driverSource) {
        this.driverSource = driverSource;
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod frameworkMethod, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                AnnotationValueExtractor<WordpressLogin, String> extractor = new AnnotationValueExtractor<WordpressLogin, String>(WordpressLogin.class);
                WordpressLogin annotation = extractor.getAnnotation(target.getClass(), frameworkMethod);
                if (annotation != null) {
                    String url = annotation.url();
                    if (url == null || url.trim().length() == 0) {
                        String urlVariable = annotation.urlVariable();
                        if (urlVariable != null && urlVariable.trim().length() > 0) {
                            Field urlVariableField = target.getClass().getDeclaredField(urlVariable);
                            urlVariableField.setAccessible(true);
                            url = (String) urlVariableField.get(target);
                        }
                    }
                    if (url != null) {
                        WordpressHelper helper = new WordpressHelper(driverSource.getDriver(), url);
                        helper.login(annotation.username(), annotation.password());
                    }
                }
                base.evaluate();
            }
        };
    }
}
