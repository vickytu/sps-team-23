package com.google.sps;

import java.net.URL;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

/**
 * Starts up the server, including a DefaultServlet that handles static files, and any servlet
 * classes annotated with the @WebServlet annotation.
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {

        // Create a server that listens on port 8080.
        Server server = new Server(8080);
        WebAppContext webAppContext = new WebAppContext();
        server.setHandler(webAppContext);

        // Load static content from inside the jar file.
        URL webAppDir = ServerMain.class.getClassLoader().getResource("META-INF/resources");
        webAppContext.setResourceBase(webAppDir.toURI().toString());

        // Enable annotations so the server sees classes annotated with @WebServlet.
        webAppContext.setConfigurations(
            new Configuration[] {
                new AnnotationConfiguration(), new WebInfConfiguration(),
        });
    }
}