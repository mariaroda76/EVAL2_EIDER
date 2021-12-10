package Utils;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogMe {

    private static LogMe logMe;

    private static FileHandler logAct;
    private static FileHandler logErr;

    private static Logger loggerE;
    private static Logger loggerA;

    private static SimpleFormatter
            formatter;


    public LogMe() {

        loggerE = Logger.getLogger ("MyELog");
        loggerA = Logger.getLogger ("MyALog");

        //Create the log file
        try {

            logAct = new FileHandler ("logs/log_actividad.log", true);
            logErr = new FileHandler ("logs/log_errors.log", true);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        loggerA.addHandler (logAct);//para añadir las lineas del log al fichero
        loggerA.setLevel (Level.INFO);
        loggerA.setUseParentHandlers (false);


        loggerE.addHandler (logErr);//para añadir las lineas del log al fichero
        loggerE.setLevel (Level.SEVERE);
        loggerE.setUseParentHandlers (false);


        formatter = new SimpleFormatter ();
        logAct.setFormatter (formatter);
        logErr.setFormatter (formatter);


        logMe = this;


    }

    public Logger getLoggerA() {
        return LogMe.loggerA;
    }

    public Logger getLoggerE() {
        return LogMe.loggerE;
    }

    public static FileHandler getLogAct() {
        return logAct;
    }

    public static FileHandler getLogErr() {
        return logErr;
    }
}