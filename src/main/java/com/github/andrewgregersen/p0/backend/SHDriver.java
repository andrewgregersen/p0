package com.github.andrewgregersen.p0.backend;

import com.github.andrewgregersen.p0.backend.commands.Analyzer;
import com.github.andrewgregersen.p0.backend.commands.DocumentMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;


public class SHDriver {

    private String cwd;
    private static final Logger log = LoggerFactory.getLogger(SHDriver.class);

    /**
     * Creates an instance of the Shell in memory, only have to init the current working directory from the system
     */

    public SHDriver() {
        this.cwd = getWorkingDirectory();
    }

    private String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }


    /**
     * Updates the current working directory to the new one
     *
     * @return: The new Working directory
     */
    private String updateCWD() {
        return this.cwd = System.getProperty("user.dir");
    }

    /**
     * Prints the current working directory
     */
    public void printWorkingDirectory() {
        System.out.println(this.cwd);
    }

    /**
     * Tells the system to update the working directory to a new one.
     *
     * @param input: The users input, contains the keyword CD, and a path to a new working directory
     * @throws IllegalArgumentException: An exception that shows simply shows the usage of the command.
     */
    public void changeCwd(String input) throws IllegalArgumentException {
        input = input.replace("cd", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to new working directory");
        try {
            String path = FileValidation.pathBuilder(input, cwd);
//            if (!pathExists(path)) //make sure that the file actually exists and is a directory.
//                throw new FileNotFoundException();
//            else {
            String old = System.setProperty("user.dir", path);
            System.out.println("Moved from " + old + " to-> " + updateCWD()); //print out the change in working directory
//            }
        } catch (InvalidPathException ex) {
            log.error("ASH: Path was mangled/malformed!");
            log.error(ex.getMessage());
            System.err.println(ex.getMessage());
        }
    }


    /**
     * Cat is a small script that prints a document out to the console a handfull of lines at a time.
     *
     * @param input: The path to the document to read from.
     * @throws FileNotFoundException:    The File does not exist or the path was mangled
     * @throws IllegalArgumentException: The command was not called correctly in the console
     */
    public void runCat(String input) throws FileNotFoundException, IllegalArgumentException {
        input = input.replace("cat", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to file");
        DocumentMethods.cat(FileValidation.pathBuilder(input, this.cwd));
    }

    /**
     * Grep is small script that searches for and prints out all occurrences of a given phrase or search term.
     *
     * @throws IllegalArgumentException: An error to show the usage of a the command
     * @throws FileNotFoundException:    The file that we are trying to search does not exist.
     */
    public void runGrep() throws IllegalArgumentException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter file pathway: ");
        String path = scanner.nextLine();
        System.out.print("Please enter the search term: ");
        String term = scanner.nextLine();
        System.out.print("Case Sensitive? Y/N: ");
        boolean sensitive;
        sensitive = scanner.nextLine().equalsIgnoreCase("y");
        DocumentMethods.grep(FileValidation.pathBuilder(path, cwd), term, sensitive);
    }

    /**
     * runAnalyzer runs the analyzer script for a document. This provides a break down of all words in the document
     * sorted by the number of times that they have appeared.
     *
     * @param input: The path to the document to breakdown
     * @throws IllegalArgumentException: An exception to show how to use the command
     * @throws FileNotFoundException:    The file either does not exist or is not located at this location.
     */

    public void runAnalyzer(String input) throws IllegalArgumentException, FileNotFoundException {
        input = input.replace("analyze", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to document to break down");
        Analyzer.runAnalyzer(FileValidation.pathBuilder(input, cwd));
    }

    /**
     * prints the children of the current working directory or specified directory.
     *
     * @param input: user given path
     */
    public void ls(String input) throws InvalidPathException, NullPointerException, IOException {
        if (input.strip().length() == 2) { //the user only passed in the chars "ls"
            Files.list(Paths.get(this.cwd).toAbsolutePath().normalize()).forEach(System.out::println);
        } else { //prints out the files in specified directory
            Files.list(Paths.get(FileValidation.pathBuilder(input.replace("ls", "").strip(), cwd)).toAbsolutePath().normalize()).forEach(System.out::println);
        }
    }


    /**
     * Tells the computer OS to run a user-defined program with all vars attached to it
     *
     * @param input read from command line
     */
    public void runOther(String input) {
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

//    private void systemFlush(Thread thread) {
//        while
//    }


}


