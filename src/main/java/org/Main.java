package org;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.config.AppConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        // Create a temporary directory for Tomcat
        String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        Context context = tomcat.addContext("", baseDir);
        tomcat.setPort(PORT);
        tomcat.getConnector();
        // Create the Spring application context
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);

        // Create and register the DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        tomcat.addServlet("", "dispatcherServlet", dispatcherServlet);
        context.addServletMappingDecoded("/*", "dispatcherServlet");

        // Start Tomcat
        tomcat.start();
        System.out.println("Tomcat started on port " + PORT);

        // Keep the application running
        tomcat.getServer().await();
    }
}