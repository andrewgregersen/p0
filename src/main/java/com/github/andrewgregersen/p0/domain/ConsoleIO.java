package com.github.andrewgregersen.p0.domain;

import com.github.andrewgregersen.p0.backend.SHDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConsoleIO {
    private static final Logger log = LoggerFactory.getLogger(ConsoleIO.class);

    public static void parseInput(String input, SHDriver driver) throws IOException {
        if (input.toLowerCase().trim().startsWith("exit"))//check to see if the user wants to exit the shell
            System.exit(0);
        else if (input.toLowerCase().trim().startsWith("cd")) //check to see if the user wants to change their directory
            driver.changeCwd(input);
        else if (input.toLowerCase().trim().startsWith("pwd")) //tell console to print the current working directory
            driver.printWorkingDirectory();
        else if (input.toLowerCase().trim().startsWith("cat")) //print out a document to console
            driver.runCat(input);
        else if (input.toLowerCase().trim().startsWith("grep")) //launch the grep ui
            driver.runGrep();
        else if (input.toLowerCase().trim().startsWith("analyze"))//analyze a document via lexical breakdown
            driver.runAnalyzer(input);
        else if (input.toLowerCase().trim().startsWith("ls")) //print the children of the current directory
            driver.ls(input);
        else if (input.toLowerCase().trim().startsWith("help"))//show usage cases for each command
            ConsoleIO.help(input);
        else if (input.toLowerCase().trim().startsWith("clear"))//clear the console
            ConsoleIO.clearConsole();
        else driver.runOther(input); //launch another program from console.
    }

    /**
     * Static command help, simply gives a brief list of commands that are built into the system.
     */

    public static void help(String input) {
        log.info("In help");
        if (input.trim().length() == 4) { //default case
            System.out.println("cd: Used to change the working directory.");
            System.out.println("pwd: Prints the current working directory.");
            System.out.println("ls: Prints the children of a given file.");
            System.out.println("help: Prints this message.");
            System.out.println("exit: Exits the shell.");
            System.out.println("clear: \"Clears\" the console.");
            System.out.println("cat: Prints a document to the command line.");
            System.out.println("grep: Searches and prints a document to the command line.");
            System.out.println("analyze: Preforms a lexical analysis on a document, prints to the command line.");
        } else {
            input = input.replace("help", "").trim();
            if (input.toLowerCase().trim().startsWith("exit"))//check to see if the user wants to exit the shell
                System.out.println("exit: Exits the shell.");
            else if (input.toLowerCase().trim().startsWith("cd")) //check to see if the user wants to change their directory
                System.out.println("Usage: [path] -> path to new working directory ");
            else if (input.toLowerCase().trim().startsWith("pwd")) //tell console to print the current working directory
                System.out.println("pwd: Prints the current working directory.");
            else if (input.toLowerCase().trim().startsWith("cat")) //print out a document to console
                System.out.println("Usage: [path] -> path to file");
            else if (input.toLowerCase().trim().startsWith("grep")) //launch the grep ui
                System.out.println("Just run the command, it has a UI to make it work.");
            else if (input.toLowerCase().trim().startsWith("analyze"))//analyze a document via lexical breakdown
                System.out.println("Usage: [path] -> path to document to break down");
            else if (input.toLowerCase().trim().startsWith("ls")) //print the children of the current directory
                System.out.println("Usage: [path] -> Path to directory, cwd by default");
            else if (input.toLowerCase().trim().startsWith("clear"))//clear the console
                System.out.println("clear: \"Clears\" the console.");
        }

    }

    /**
     * "Clears" the users console by printing 30 lines of blank text
     */
    public static void clearConsole() {
        for (int i = 0; i < 30; i++)
            System.out.println("\u001b[0m");
    }

}
