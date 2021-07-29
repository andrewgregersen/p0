package com.github.andrewgregersen.p0.backend;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileValidation {

    /**
     * @param s:   The path to the new Working Directory.
     * @param cwd: The current working directory, could be called from System.getProperty("user.dir")
     *             but it should be stored in the calling class.
     * @return A new String that represents the new working directory
     * @throws InvalidPathException: The path passed by the user is trying to access a parent of a child they are asking of
     */

    protected static String pathBuilder(String s, String cwd) throws InvalidPathException {
        //if the path is just a reference to the parent directory, return the parent.
        if (s.equalsIgnoreCase("../"))
            return getParent(cwd).toString(); //user just wants the parent directory
        if (s.equalsIgnoreCase("~")) //user wants to return to their home directory
            return System.getProperty("user.home");
        Path path = normalize(cwd);
        String[] frags = removeBackSlash(s).split("/");
        StringBuilder builder = new StringBuilder();
        boolean ok = true;
        //iterate through the new pathway
        for (String f : frags) {
            if (f.equals("..") && ok)
                path = getParent(path); //get the parent directory
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
            builder.append(normalize(path).toString());
        if (pathExists(builder.toString()))//validate that the file exists
            return builder.toString(); //return the new pathway
        else throw new InvalidPathException(builder.toString(), "The File does not exist");
    }

    private static String removeBackSlash(String s) {
        return s.replaceAll("\\\\", "/").replaceAll("\"", "").trim();
    }

    /**
     * @param path: The string representation of a file
     * @return: If the file exists
     */
    private static boolean pathExists(String path) {
        return Paths.get(path).toAbsolutePath().toFile().exists();
    }

    //Normalizes the path of a path object

    private static Path normalize(Path path) {
        return path.toAbsolutePath().normalize();
    }

    private static Path normalize(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }

    //returns the parent of a path.

    private static Path getParent(Path path) {
        return path.getParent().toAbsolutePath().normalize();
    }

    private static Path getParent(String path) {
        return Paths.get(path).getParent().toAbsolutePath().normalize();
    }
}
