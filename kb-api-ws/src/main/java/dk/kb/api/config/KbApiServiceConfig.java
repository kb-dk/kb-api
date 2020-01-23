package dk.kb.api.config;

import dk.kb.util.YAML;

import java.io.IOException;
import java.util.List;

public class KbApiServiceConfig {
    /**
     * Besides parsing of YAML files using SnakeYAML, the YAML helper class provides convenience
     * methods like {@code getInteger("someKey", defaultValue)} and {@code getSubMap("config.sub1.sub2")}.
     */
    private static YAML serviceConfig;

    /**
     * Initialized the configuration from the provided configFile.
     * This should normally be called from {@link dk.kb.api.webservice.APIContextListener} as
     * part of web server initialization of the container.
     * @param configFile the configuration to load.
     * @throws IOException if the configuration could not be loaded or parsed.
     */
    public static synchronized void initialize(String configFile) throws IOException {
        serviceConfig = new YAML(configFile);
    }

    /**
     * Demonstration of a first-class property, meaning that an explicit method has been provided.
     * @see #getConfig() for alternative.
     * @return the "Hello World" lines defined in the config file.
     */
    public static List<String> getHelloLines() {
        List<String> lines = serviceConfig.getList("config.helloLines");
        return lines;
    }

    /**
     * Direct access to the backing YAML-class is used for configurations with more flexible content
     * and/or if the service developer prefers key-based property access.
     * @see #getHelloLines() for alternative.
     * @return the backing YAML-handler for the configuration.
     */
    public static YAML getConfig() {
        if (serviceConfig == null) {
            throw new IllegalStateException("The configuration should have been loaded, but was not");
        }
        return serviceConfig;
    }
}
