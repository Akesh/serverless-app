package com.payu.lambda.common;


import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.payu.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public abstract class BaseRequestHandler<R, O> implements RequestHandler<R, O> {

    private static ApplicationContext springContext = null;

    private static ApplicationContext getSpringContext() {
        if (springContext == null) {
            synchronized (ApplicationContext.class) {
                if (springContext == null) {
                    System.out.println("Creating spring application context");
                    springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
                }
            }
        }
        return springContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getSpringContext().getBean(clazz);
    }
}
