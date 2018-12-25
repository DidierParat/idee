package idee;

import idee.client.DntClient;
import idee.client.LocationForecastClient;
import idee.resource.GetIdeasResource;
import idee.util.MapOfNorway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

@Slf4j
public final class Main {

  public static void main(final String[] args) throws Exception {
    Config config;
    try {
      config = new Config(args);
    } catch (ParseException exception) {
      log.info("Could not parse args. Exception caught: ", exception);
      return;
    }

    final LocationForecastClient locationForecastClient = new LocationForecastClient();
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
