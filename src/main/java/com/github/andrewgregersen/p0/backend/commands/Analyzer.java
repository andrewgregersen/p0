package com.github.andrewgregersen.p0.backend.commands;

import com.github.andrewgregersen.p0.backend.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Analyzer {

    private static final Log log = Log.of(Analyzer.class);

    public static void runAnalyzer(String path) throws IOException {
        try {
            log.debug("Starting analyzer...");
            TreeMap<String, Integer> termMap = (TreeMap<String, Integer>) getTerms(path);
            System.out.println("Term : Occurrences");
            Set<String> keys = termMap.keySet();
            for (String key : keys) {
                if (key.length() != 1)
                    System.out.println(key + " : " + termMap.get(key));
            }
        } catch (IOException ex) {
            log.error("Something went wrong " + ex.getMessage(), ex.getCause());
        }

    }

    /**
     * @param path: The path to the file that is to be read
     * @return A map o keys and the number of times that key occurs
     * @throws IOException The file does not exist or an error occurs in reading the file
     */
    private static Map<String, Integer> getTerms(String path) throws IOException {
        log.debug("Parsing file");
        Map<String, Integer> termMap = new TreeMap<>();
        Files.lines(Paths.get(path)).forEach(it -> {
            Arrays.stream(it.split(" ")).forEach(it1 -> {
                if (termMap.putIfAbsent(it1, 1) != null)
                    termMap.put(it1, termMap.get(it1) + 1);
            });
        });
        return termMap;
    }
}
