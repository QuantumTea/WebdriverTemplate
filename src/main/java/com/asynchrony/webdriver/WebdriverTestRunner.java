package com.asynchrony.webdriver;

import com.asynchrony.webdriver.rules.*;
import org.junit.rules.MethodRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class WebdriverTestRunner extends BlockJUnit4ClassRunner {
    public WebdriverTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<MethodRule> rules(Object test) {
        List<MethodRule> rules = new ArrayList<MethodRule>();
        rules.addAll(super.rules(test));
        InjectWebdriverRule webdriverRule = new InjectWebdriverRule();

        rules.add(new TimerRule());
        rules.add(new InjectConfigRule());
        rules.add(new InjectMocksRule());
        rules.add(webdriverRule);
        rules.add(new InjectHelperRule(webdriverRule));
        return rules;
    }

    @Override
    protected Statement withAfterClasses(final Statement statement) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } finally {
                    Log.writeFinalLog();
                }
            }
        };
    }
}
