package com.asynchrony.webdriver.rules;

import com.asynchrony.webdriver.Log;
import com.asynchrony.webdriver.annotations.Config;
import com.asynchrony.webdriver.annotations.InjectProperty;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class InjectConfigRule implements MethodRule {
    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        injectConfig(method, target);
        return base;
    }

    private void injectConfig(FrameworkMethod method, Object target) {
        Class<?> klass = target.getClass();
        String configPath = getConfigPath(klass, method);
        if (configPath == null) {
            return;
        }

        InputStream inputStream = klass.getResourceAsStream(configPath);
        if (inputStream == null) {
            return;
        }

        Properties config = new Properties();
        try {
            config.load(inputStream);
            Log.setLogFilePath(config.getProperty("logfilepath", "c:\\Logs"));
            Log.setLogFileName(config.getProperty("logfilename", target.getClass().getName()+".txt"));

            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                InjectProperty injection = field.getAnnotation(InjectProperty.class);
                if (injection != null) {
                    String propertyValue = config.getProperty(injection.value());
                    Class<?> fieldType = field.getType();
                    try {
                        if (fieldType.isAssignableFrom(String.class)) {
                            field.setAccessible(true);
                            field.set(target, propertyValue);
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("### Config failed to load: " + e.getMessage());
        }
    }

    private String getConfigPath(Class<?> klass, FrameworkMethod method) {
        return new AnnotationValueExtractor<Config, String>(Config.class).value(klass, method);
    }
}
