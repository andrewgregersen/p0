package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;


/**
 * Main class to make the program. Uses threads for running the users commands and then waits for those commands to finish
 * before continuing.
 */
public class ASH {


    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(ASH.class);
        SHDriver driver = new SHDriver();
        log.info("Starting driver");
        String input;
        try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            driver.changeCwd("~");
            while (true) { //have the program run until the user exits the shell
                log.info("Waiting for user input");
                System.out.print("$:> ");
                input = stdin.readLine();
                if (input.toLowerCase().trim().startsWith("exit"))//check to see if the user wants to exit the shell
                    break;
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
                    driver.help(input);
                else if (input.toLowerCase().trim().startsWith("clear"))//clear the console
                    clearConsole();
                else driver.runOther(input); //launch another program from console.
            }
        } catch (InvalidPathException | FileNotFoundException ex) {
            log.error("Something went wrong: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * "Clears" the users console by printing 30 lines of blank text
     */
    private static void clearConsole() {
        for (int i = 0; i < 30; i++)
            System.out.println("\u001b[0m");
    }
}
