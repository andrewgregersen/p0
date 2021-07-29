package com.github.andrewgregersen.p0.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private final Class<?> aClass;
    private final Logger log;
    private static final BufferedWriter writer = initWriter();

    private static BufferedWriter initWriter() {
        BufferedWriter temp;
        try {
            return new BufferedWriter(new FileWriter("log.txt"));
        } catch (IOException ex) {
            Logger logger = LoggerFactory.getLogger(Log.class);
            logger.error("Failed to read log.txt" + ex.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public static Log of(Class<?> clazz) {
        return new Log(clazz);
    }

    public Log(Class<?> clazz) {
        this.aClass = clazz;
        this.log = LoggerFactory.getLogger(clazz);
    }


    public void info(String message) throws IOException {
        log.info(message);
        output("INFO (" + aClass.getName() + "): " + message);
    }

    public void error(String message, Throwable cause) throws IOException {
        log.error(message, cause);
        output("ERROR (" + aClass.getName() + "): " + message);
    }

    public void error(String message) throws IOException {
        log.error(message);
        output("ERROR (" + aClass.getName() + "): " + message);
    }

    public void debug(String message) throws IOException {
        log.debug(message);
        output("DEBUG (" + aClass.getName() + "): " + message);
    }

    private static void output(String message) throws IOException {
        File file = new File("log.txt");
        file.createNewFile();
        writer.append(message).append("\n");
        writer.flush();
    }
}
