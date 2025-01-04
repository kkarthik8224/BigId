package com.stringmatcher.utils;

import java.util.logging.*;

public class Log {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to set up logger", e);
        }
    }
    
    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }

    public static void logSevere(String message) {
        logger.severe(message);
    }
    
}
