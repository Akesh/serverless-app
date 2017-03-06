package com.payu.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by akesh.patil on 04-03-2017.
 */
public class SpringTester {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.payu");//This will load the configured components UserService, UserRepository,
        ctx.refresh();
        System.out.println(ctx);
    }
}
