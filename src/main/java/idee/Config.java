package idee;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Created by didier on 14.06.17.
 */
public class Config {
    public static String dntHost;
    public static String dntApiKey;
    public Config(final String[] args) {
        // Parse args
        Options options = new Options();
        Option dntHostOption = new Option("", "dntHost", true, "DNT host");
        dntHostOption.setRequired(true);
        options.addOption(dntHostOption);
        Option dntApiKeyOption = new Option("", "dntApiKey", true, "DNT API key");
        dntApiKeyOption.setRequired(true);
        options.addOption(dntApiKeyOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
        }

        this.dntHost = cmd.getOptionValue("dntHost");
        this.dntApiKey = cmd.getOptionValue("dntApiKey");
    }
}
