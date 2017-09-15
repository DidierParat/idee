package idee;

import idee.Clients.AdaptiveClient;
import idee.Clients.DntClient;
import idee.Clients.LocationForecastClient;
import java.util.logging.Logger;
import org.apache.commons.cli.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import org.glassfish.jersey.server.*;
import org.glassfish.jersey.servlet.*;

public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        Config config;
        try {
            config = new Config(args);
        } catch (ParseException e) {
            LOGGER.severe("Could not parse args." + e);
            return;
        }

        final LocationForecastClient locationForecastClient
                = new LocationForecastClient();
        final DntClient dntClient = new DntClient(config.dntHost, config.dntApiKey);
        final MapOfNorway mapOfNorway = new MapOfNorway(dntClient);

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(
                new GetIdeasResource(dntClient, locationForecastClient, mapOfNorway));
        ServletHolder servlet = new ServletHolder(new ServletContainer(resourceConfig));

        Server server = new Server(config.port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");
        try {
            server.start();
        } catch (Exception e) {
            LOGGER.severe("Could not start server." + e);
        }
    }

    private Main() {
        //not called
    }
}
