package com.github.andrewgregersen.p0.interfaces;

import java.io.IOException;

public interface ConsoleIOInterface {


    void parseInput(String input) throws IOException, InterruptedException;

    void help(String input) throws IOException, InterruptedException;

    void clearConsole();

}
