package com.github.andrewgregersen.p0.domain;

import com.github.andrewgregersen.p0.backend.Log;
import com.github.andrewgregersen.p0.backend.SHDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;

import static com.github.andrewgregersen.p0.domain.ConsoleIO.parseInput;


/**
 * Main class to make the program. Uses threads for running the users commands and then waits for those commands to finish
 * before continuing.
 */
public class ASH {


    public static void main(String[] args) throws IOException {

        Log log = Log.of(ASH.class);
        log.debug("Starting driver");
        String input;
        SHDriver driver = new SHDriver();
        try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) { //have the program run until the user exits the shell
                log.info("Waiting for user input");
                System.out.print("$:> ");
                input = stdin.readLine();
                if (!input.isBlank()) {
                    parseInput(input, driver);
                }
            }
        } catch (InvalidPathException | FileNotFoundException ex) {
            log.error("Something went wrong: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
        } catch (IOException e) {
            log.error(String.valueOf(e.fillInStackTrace()));
        }


    }


}
