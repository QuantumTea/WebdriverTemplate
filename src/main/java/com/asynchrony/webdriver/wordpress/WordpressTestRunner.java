package com.asynchrony.webdriver.wordpress;

import com.asynchrony.webdriver.WebdriverTestRunner;
import com.asynchrony.webdriver.wordpress.rules.WordpressLoginRule;
import org.junit.rules.MethodRule;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

public class WordpressTestRunner extends WebdriverTestRunner {
    public WordpressTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<MethodRule> rules(Object test) {
        List<MethodRule> rules = new ArrayList<>();
        rules.add(new WordpressLoginRule());
        rules.addAll(super.rules(test));
        return rules;
    }
}
