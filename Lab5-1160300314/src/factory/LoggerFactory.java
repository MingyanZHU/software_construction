package factory;

import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory {

  /**
   * Get own Log4j Logger.
   *
   * @param clazz the own class
   * @return Log4j.Logger instance
   */
  public static org.apache.log4j.Logger createLogger(Class clazz) {
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(clazz);
    PropertyConfigurator.configure("src/log4j.properties");
    return logger;
  }
}
