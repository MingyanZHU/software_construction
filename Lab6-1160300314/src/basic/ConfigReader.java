package basic;

import java.io.File;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.PropertyConfigurator;

public class ConfigReader {

  private static final Configurations configs = new Configurations();
  private static final File file = new File("src/config.properties");
  private static PropertiesConfiguration config;

  /*
    Rep invariant:
      configs != null and config != null
    Abstraction function:
      represents a reader read from config.properties.
    Safety from rep exposure:
      all fields are private and immutable
    Thread safety arguments:
      there is no other methods to access all fields, and config and configs is
      Thread-Safe class instance.
      详见链接 https://commons.apache.org/proper/commons-configuration/apidocs/index.html
   */
  /**
   * For reading properties from config.properties.
   *
   * @param params want to read.
   * @return the values of params.
   * @throws ConfigurationException if there is no such config.
   */
  public static int getConfig(Params params) throws ConfigurationException {
    init();
    return config.getInt(params.name());
  }

  private static void init() throws ConfigurationException {
    PropertyConfigurator.configure("log4j.properties");
    config = configs.properties(file);
    FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
  }
}
