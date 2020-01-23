package dk.kb.api.webservice;

import dk.kb.api.config.KbApiServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener to handle the various setups and configuration sanity checks that can be carried out at when the
 * context is deployed/initalized.
 */

public class APIContextListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * On context initialisation this
     * i) Initialises the logging framework (logback).
     * ii) Initialises the configured DorqBackends 
     * @param sce
     * @throws java.lang.RuntimeException if anything at all goes wrong.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("Initializing kb-api service v{}", getClass().getPackage().getImplementationVersion());
            InitialContext ctx = new InitialContext();
            String configFile = (String) ctx.lookup("java:/comp/env/application-config");
            KbApiServiceConfig.initialize(configFile);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup settings", e);
        } catch (Exception e) {
            log.error("failed to initialize service", e);
            throw new RuntimeException("failed to initialize service", e);
        }
        log.info("Template service initialized.");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("Template service destroyed");
    }

}
