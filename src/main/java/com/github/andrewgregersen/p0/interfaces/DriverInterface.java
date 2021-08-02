package com.github.andrewgregersen.p0.interfaces;

import java.io.IOException;
import java.nio.file.InvalidPathException;

public interface DriverInterface {

    /**
     * A method to return the current working directory as per the JVM.
     * Makes a system call.
     *
     * @return: The Current Directory that Java is working out of.
     */
    default String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Updates the current working directory to the new one
     *
     * @return: The new Working directory
     */
    default String updateCWD() {
        return System.getProperty("user.dir");
    }

    void printWorkingDirectory();

    void changeCwd(String input) throws IllegalArgumentException, IOException;

    void runCat(String input) throws IOException, IllegalArgumentException;

    void runGrep() throws IllegalArgumentException, IOException;

    void runAnalyzer(String input) throws IllegalArgumentException, IOException;

    void ls(String input) throws InvalidPathException, NullPointerException, IOException;

    void runOther(String input) throws IOException;
}
