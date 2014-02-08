package com.asynchrony.webdriver.rules;

import com.asynchrony.webdriver.Log;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TimerRule implements MethodRule {
    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    double elapsed = (System.currentTimeMillis() - startTime) / 1000.0;
                    Log.info(method.getName() + " - " + elapsed + " seconds.");
                }
            }
        };
    }

}
