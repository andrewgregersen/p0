package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
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

    private boolean pathExists(String path) {
        return Paths.get(path).toAbsolutePath().toFile().exists();
    }

    /**
     * Updates the current working directory to the new one
     *
     * @return: The new Working directory
     */
    private String updateCWD() {
        return this.cwd = System.getProperty("user.dir");
    }

    public void printWorkingDirectory() {
        System.out.println(this.cwd);
    }

    /**
     * @param input: The users input, contains the keyword CD, and a path to a new working directory
     * @throws IllegalArgumentException: An exception that shows simply shows the usage of the command.
     */
    public void changeCwd(String input) throws IllegalArgumentException {
        input = input.replace("cd", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to new working directory");
        try {
            String path = pathBuilder(input);
            if (!pathExists(path)) //make sure that the file actually exists and is a directory.
                throw new FileNotFoundException();
            else {
                String old = System.setProperty("user.dir", path);
                System.out.println("Moved from " + old + " to-> " + updateCWD()); //print out the change in working directory
            }
        } catch (FileNotFoundException ex) {
            log.error("ASH: File does not exist!");
        } catch (InvalidPathException ex) {
            log.error("ASH: Path was mangled/malformed!");
            log.error(ex.getMessage());
        }
    }


    /**
     * @param s: The path to the new Working Directory.
     * @return A new String that represents the new working directory
     * @throws InvalidPathException: The path passed by the user is trying to access a parent of a child they are asking of
     */
    private String pathBuilder(String s) throws InvalidPathException {
        //if the path is just a reference to the parent directory, return the parent.
        if (s.equalsIgnoreCase("../"))
            return Paths.get(cwd).getParent().toAbsolutePath().normalize().toString(); //user just wants the parent directory
        if (s.equalsIgnoreCase("~")) //user wants to return to their home directory
            return System.getProperty("user.home");


        Path path = Paths.get(cwd).toAbsolutePath().normalize();
        String[] frags = s.replaceAll("\\\\", "/").replaceAll("\"", "").trim().split("/");
        StringBuilder builder = new StringBuilder();
        boolean ok = true;


        //iterate through the new pathway
        for (String f : frags) {
            if (f.equals("..") && ok)
                path = Paths.get(path.toString()).toAbsolutePath().getParent(); //get the parent directory
            else {
                if (f.contains(":")) {
                    builder.append(f.trim()).append("\\");
                    ok = false;
                    continue;
                } else if (ok) { //there is no more ../ in the path
                    ok = false;
                    builder.append(path.toString()).append("\\");
                } else if (f.equals("../"))//some one was being cheeky
                    throw new InvalidPathException(f, "You had ../ in the middle of a path!");
                builder.append(f.trim()).append("\\"); //otherwise just keep making the new path
            }
        }
        if (builder.length() == 0) //the only path passed in directed to a parent folder.
            builder.append(path.toAbsolutePath().normalize().toString());
        return builder.toString(); //return the new pathway
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
        String path = pathBuilder(input);
        if (pathExists(path))
            DocumentMethods.cat(path);
        else throw new FileNotFoundException("This file does not exist!");
    }

    /**
     * Grep is small script that searches for and prints out all occurances of a given phrase or search term.
     *
     * @throws IllegalArgumentException: An error to show the usage of a the command
     * @throws FileNotFoundException:    The file that we are trying to search does not exist.
     */
    public void runGrep() throws IllegalArgumentException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        String pathTo;

        System.out.print("Please enter file pathway: ");
        String path = scanner.nextLine();
        System.out.print("Please enter the search term: ");
        String term = scanner.nextLine();
        System.out.print("Case Sensitive? Y/N: ");
        boolean sensitive;
        sensitive = scanner.nextLine().equalsIgnoreCase("y");
        if (!pathExists(pathTo = pathBuilder(path)))
            throw new IllegalArgumentException("You did not provide a valid path");
        DocumentMethods.grep(pathTo, term, sensitive);


    }

    /**
     * runAnalyzer runs the analyzer script for a document. This provides a break down of all words in the document
     * sorted by the number of times that they have appeard.
     *
     * @param input: The path to the document to breakdown
     * @throws IllegalArgumentException: An exception to show how to use the command
     * @throws FileNotFoundException:    The file either does not exist or is not located at this location.
     */

    public void runAnalyzer(String input) throws IllegalArgumentException, FileNotFoundException {
        input = input.replace("analyze", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new IllegalArgumentException("Usage: [path] -> path to document to break down");
        String path = pathBuilder(input);
        if (pathExists(path))
            Analyzer.runAnalyzer(path);
        else throw new FileNotFoundException("This file does not exist!");

    }

    /**
     * prints the children of the current working directory or specified directory.
     *
     * @param input: user given path
     */
    public void ls(String input) throws InvalidPathException, NullPointerException, IOException {
        if (input.strip().length() == 2) { //the user only passed in the chars "ls"
            Path cwd = Paths.get(this.cwd);
            Files.list(cwd.toAbsolutePath().normalize()).forEach(System.out::println);
        } else { //prints out the files in specified directory
            input = input.replace("ls", "").strip();
            String path = pathBuilder(input);
            if (pathExists(path)) {
                Files.list(Paths.get(path).toAbsolutePath().normalize()).forEach(System.out::println);
            } else throw new FileNotFoundException("The File does not exist!");
        }
    }

    /**
     * Static command help, simply gives a brief list of commands that are built into the system.
     */

    public void help(String input) {
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
        }

    }

//    private void systemFlush(Thread thread) {
//        while
//    }


}


