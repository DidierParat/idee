package idee;

import idee.clients.DntClient;
import idee.clients.LocationForecastClient;
import idee.resources.GetIdeasResource;
import idee.utils.MapOfNorway;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.logging.Logger;

public final class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(final String[] args) throws Exception {
    Config config;
    try {
      config = new Config(args);
    } catch (ParseException exception) {
      LOGGER.severe("Could not parse args." + exception);
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
    server.start();
  }

  private Main() {
    //not called
  }
}
