package com.github.andrewgregersen.p0.domain;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;


/**
 * Main class to make the program. Used to access the ConsoleIO API
 */
public class ASH {

    private static final ConsoleIO console = new ConsoleIO();
    private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("logger");

    public static void main(String[] args) throws IOException {
        log.debug("Starting driver");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input;
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
        while (true) {
            try {
                //have the program run until the user exits the shell
                log.info("Waiting for user input");
                System.out.print("$:> ");
                input = stdin.readLine();
                if (!input.isBlank()) {
                    console.parseInput(input);
                }

            } catch (InvalidPathException | FileNotFoundException ex) {
                log.error("Something went wrong: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                log.error(ex.getMessage());
            } catch (IOException e) {
                log.error(String.valueOf(e.fillInStackTrace()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


}
