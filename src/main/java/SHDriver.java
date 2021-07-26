import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

class ErrException extends Exception {
    private final String message;

    public ErrException(String Message) {
        this.message = Message;
    }

    public String getMessage() {
        return message;
    }

}

public class SHDriver {

    private String cwd;
    private Logger logger;

    public SHDriver() {
        this.cwd = getWorkingDirectory();
    }

    private String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public void setCwd(String input) throws ErrException {
        input = input.replace("cd", "").strip().replaceAll("\"", "").strip();
        if (input.length() == 0)
            throw new ErrException("Usage: [path] -> path to new working directory");
        try {
            String path = pathBuilder(input);
            if (!Paths.get(path).toAbsolutePath().toFile().exists()) //make sure that the file actually exists and is a directory.
                throw new FileNotFoundException();
            else {
                String old = System.setProperty("user.dir", path);
                System.out.println("Moved from " + old + " to-> " + updateCWD()); //print out the change in working directory
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ASH: File does not exist!");
        } catch (InvalidPathException ex) {
            System.out.println("ASH: Path was mangled/malformed!");
            System.out.println(ex.getMessage());
        }
    }

    private String pathBuilder(String s) throws InvalidPathException {
        //if the path is just a reference to the parent directory, return the parent.
        if (s.equalsIgnoreCase("../"))
            return Paths.get(cwd).getParent().toAbsolutePath().normalize().toString(); //user just wants the parent directory


        Path path = Paths.get(cwd).toAbsolutePath().normalize();
        String[] frags = s.replaceAll("\\\\", "/").split("/");
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


    private String updateCWD() {
        this.cwd = System.getProperty("user.dir");
        return cwd;
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
            assert f != null;
            for (File file : f) {
                System.out.println(file.getName());
            }
//        } else if (input.equals("../")) {
//            Path cwd = Paths.get(this.cwd).getParent();
//            File[] f = cwd.toFile().listFiles();
//            for (File file : f) {
//                System.out.println(file.getName());
//            }
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
