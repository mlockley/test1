package com.jpmc.ib.cdt.clearing;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    private final ClassPathXmlApplicationContext ctx;

    public static void main( String[] args )
    {
        new App();
    }

    public App() {
        ctx = new ClassPathXmlApplicationContext("spring.xml");
    }

}
