package com.asynchrony.webdriver.rules;

import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;

public class AnnotationValueExtractor<A extends Annotation, V>
{
    private final Class<A> annotationClass;

    public AnnotationValueExtractor(Class<A> annotationClass)
    {
        this.annotationClass = annotationClass;
    }

    @SuppressWarnings("unchecked")
    public V value(Class<?> klass, FrameworkMethod method)
    {
        A annotation = getAnnotation(klass, method);
        if (annotation == null) return null;
        try
        {
            return (V) annotationClass.getMethod("value", new Class[0]).invoke(annotation, new Class[0]);
        } catch (Exception e)
        {
            return null;
        }
    }

    public A getAnnotation(Class<?> klass, FrameworkMethod method) {
        A klassAnnotation = klass.getAnnotation(annotationClass);
        A methodAnnotation = method.getAnnotation(annotationClass);
        if (klassAnnotation == null && methodAnnotation == null)
        {
            return null;
        }

        A annotation = methodAnnotation != null ? methodAnnotation : klassAnnotation;
        return annotation;
    }
}
