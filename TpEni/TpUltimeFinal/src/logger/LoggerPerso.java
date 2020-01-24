package logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerPerso {

    public static FileHandler fh = null;
    public static ConsoleHandler ch = null;

    /**
     * Permet de récuperer un logger.
     **/
    public static Logger getLogger(String className)
    {
        Logger logger = Logger.getLogger(className);

        logger .setLevel(Level.FINEST);
        logger .setUseParentHandlers(false);

        if(ch == null)
        {
            ch = new ConsoleHandler();
            ch.setLevel(Level.FINEST);
        }

        if(fh == null)
        {
            try
            {
                fh = new FileHandler("beDeveloper.log");
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
        }

        logger .addHandler(ch);
        logger .addHandler(fh);

        return logger ;
    }
}
