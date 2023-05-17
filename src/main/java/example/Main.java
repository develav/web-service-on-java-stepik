package example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resourceServer.ResourceServer;
import servlets.ResourcesServlet;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {

        ResourceServer resourceServer = new ResourceServer();
        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ResourcesServlet(resourceServer)), ResourcesServlet.PAGE_URL);
        server.setHandler(context);

        server.start();
        logger.info("Server started");
        server.join();
    }
}
