package factory;

import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory {
    public static org.apache.log4j.Logger createLogger(Class clazz){
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(clazz);
        PropertyConfigurator.configure("src/log4j.properties");
        return logger;
    }
}
