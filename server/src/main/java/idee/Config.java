package idee;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Config {

  private static final String ARG_DNT_API_KEY = "dntApiKey";
  private static final String ARG_DNT_HOST = "dntHost";
  private static final String ARG_PORT = "port";
  private static final String ARG_ROME2RIO_API_KEY = "rome2RioApiKey";
  private static final String ARG_ROME2RIO_HOST = "rome2RioHost";

  public final String dntHost;
  public final String dntApiKey;
  public final Integer port;

  public Config(final String[] args) throws ParseException {
    Options options = new Options();

    Option dntHostOption
        = new Option(ARG_DNT_HOST, ARG_DNT_HOST, true, "DNT host.");
    dntHostOption.setRequired(true);
    options.addOption(dntHostOption);

    Option dntApiKeyOption
        = new Option(ARG_DNT_API_KEY, ARG_DNT_API_KEY, true, "DNT API key.");
    dntApiKeyOption.setRequired(true);
    options.addOption(dntApiKeyOption);

    Option port = new Option(ARG_PORT, ARG_PORT, true, "Server port number.");
    port.setRequired(true);
    options.addOption(port);

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd;
    cmd = parser.parse(options, args);

    this.dntHost = cmd.getOptionValue(ARG_DNT_HOST);
    this.dntApiKey = cmd.getOptionValue(ARG_DNT_API_KEY);
    this.port = Integer.parseInt(cmd.getOptionValue(ARG_PORT));
  }
}
