package com.github.andrewgregersen.p0.backend;


import com.github.andrewgregersen.p0.backend.commands.Analyzer;
import com.github.andrewgregersen.p0.backend.commands.DocumentMethods;
import com.github.andrewgregersen.p0.interfaces.DriverInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;


public class SHDriver implements DriverInterface {

    private String cwd;
    private static final Logger log = LoggerFactory.getLogger("logger.Driver");
    private static final FileValidation fileValidator = new FileValidation();


    /**
     * Creates an instance of the Shell in memory, only have to init the current working directory from the system
     */

    public SHDriver() {
        this.cwd = getWorkingDirectory();
    }


    @Override
    public String updateCWD() {
        return this.cwd = System.getProperty("user.dir");
    }


    /**
     * Prints the current working directory
     */
    @Override
    public void printWorkingDirectory() {
        System.out.println(this.cwd);
    }

    /**
     * Tells the system to update the working directory to a new one.
     *
     * @param input: The users input, contains the keyword CD, and a path to a new working directory
     * @throws IllegalArgumentException: An exception that shows simply shows the usage of the command.
     */
    @Override
    public void changeCwd(String input) throws IllegalArgumentException, IOException {
        input = input.replace("cd", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to new working directory");
        try {
            String path = fileValidator.pathBuilder(input, cwd);
            String old = System.setProperty("user.dir", path);
            System.out.println("Moved from " + old + " to-> " + updateCWD()); //print out the change in working directory
            log.debug("Moved from " + old + " to " + cwd);
        } catch (InvalidPathException ex) {
            log.error("ASH: Path was mangled/malformed!", ex.getCause());
            log.error(ex.getMessage(), ex.getCause());
            System.err.println(ex.getMessage());
        }
    }


    /**
     * Cat is a small script that prints a document out to the console a handfull of lines at a time.
     *
     * @param input: The path to the document to read from.
     * @throws IOException:              The File does not exist or the path was mangled
     * @throws IllegalArgumentException: The command was not called correctly in the console
     */
    @Override
    public void runCat(String input) throws IOException, IllegalArgumentException {
        input = input.replace("cat", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to file");
        DocumentMethods.cat(fileValidator.pathBuilder(input, this.cwd));
    }

    /**
     * Grep is small script that searches for and prints out all occurrences of a given phrase or search term.
     *
     * @throws IllegalArgumentException: An error to show the usage of a the command
     * @throws IOException:              The file that we are trying to search does not exist or something else went wrong.
     */
    @Override
    public void runGrep() throws IllegalArgumentException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter file pathway: ");
        String path = scanner.nextLine();
        System.out.print("Please enter the search term: ");
        String term = scanner.nextLine();
        System.out.print("Case Sensitive? Y/N: ");
        boolean sensitive;
        sensitive = scanner.nextLine().equalsIgnoreCase("y");
        DocumentMethods.grep(fileValidator.pathBuilder(path, cwd), term, sensitive);
    }

    /**
     * runAnalyzer runs the analyzer script for a document. This provides a break down of all words in the document
     * sorted by the number of times that they have appeared.
     *
     * @param input: The path to the document to breakdown
     * @throws IllegalArgumentException: An exception to show how to use the command
     * @throws IOException:              The file either does not exist or is not located at this location.
     */

    @Override
    public void runAnalyzer(String input) throws IllegalArgumentException, IOException {
        input = input.replace("analyze", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to document to break down");
        Analyzer.runAnalyzer(fileValidator.pathBuilder(input, cwd));
    }

    /**
     * prints the children of the current working directory or specified directory.
     *
     * @param input: user given path
     */
    @Override
    public void ls(String input) throws InvalidPathException, NullPointerException, IOException {
        if (input.strip().length() == 2) { //the user only passed in the chars "ls"
            Files.list(Paths.get(this.cwd).toAbsolutePath().normalize()).forEach(System.out::println);
        } else { //prints out the files in specified directory
            Files.list(Paths.get(fileValidator.pathBuilder(input.replace("ls", "").
                    strip(), cwd)).toAbsolutePath().normalize()).forEach(System.out::println);
        }
    }


    /**
     * Tells the computer OS to run a user-defined program with all vars attached to it.
     * Should be upgraded to use threads
     *
     * @param input read from command line
     */
    @Override
    public void runOther(String input) throws IOException {
        try {
            String[] command = input.trim().split(" ");
            log.info("Building external command");
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            log.info("Starting External Command");
            Process process = pb.start();
            //Thread waitThread = new Thread(systemFlush(this);
            while (process.isAlive()) { //make this a new thread to keep tack of the system
                System.out.flush();
                System.err.flush();
            }


        } catch (IllegalArgumentException | IOException e) {
            log.error(e.getMessage(), e.getCause());
            System.err.println("A problem occurred..." + e.getMessage());
            System.out.println();
        }

    }


}


