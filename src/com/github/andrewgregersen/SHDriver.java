package com.github.andrewgregersen;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SHDriver {

    private String cwd;

    public SHDriver() {
        this.cwd = getWorkingDirectory();
    }

    private String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public void setCwd(String input) {
        System.setProperty("user.dir", input.split(" ")[1]);
        updateCWD();
    }

    private void updateCWD() {
        this.cwd = System.getProperty("user.dir");
    }

    public String getCwd() {
        return cwd;
    }

    public void printWorkingDirectory() {
        System.out.println(cwd);
    }

    public void runCat(String input) {

    }

    public void runGrep(String input) {


    }

    public void runAnalyzer(String input) {

    }

    /**
     * prints the children of the current working directory or specified directory.
     *
     * @param input: user given path
     */
    public void ls(String input) throws InvalidPathException, NullPointerException {
        if (input.strip().length() == 2) {
            Path cwd = Paths.get(this.cwd);
            File[] f = cwd.toFile().listFiles();
            for (File file : f) {
                System.out.println(file.getName());
            }
        } else { //prints out the files in specified directory
            input = input.replace("ls", "").strip();
            String path = input.replaceAll("\"", ""); //remove all '"' from new path
            File[] wd = Paths.get(path).toAbsolutePath().normalize().toFile().listFiles();
            for (File a : wd) {
                System.out.println(a);
            }
        }
    }

    /**
     * Tells the computer OS to run a user-defined program with all vars attached to it
     *
     * @param input read from command line
     */
    public void runOther(String input) {

    }

//    private String buildCommand() {
//
//    }

}
