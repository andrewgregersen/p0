import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DocumentMethods {

    public static void grep(String path, String term, Boolean sensitive) {
        try {
            if (sensitive) {
                Files.lines(Paths.get(path)).filter(it -> it.contains(term)).forEach(System.out::println);
            } else
                Files.lines(Paths.get(path)).filter(it -> it.toLowerCase().contains(term.toLowerCase())).forEach(System.out::println);
        } catch (IOException ex) {
            System.err.println("Something went wrong... " + ex.getMessage());
        }
    }

    public static void cat(String path) {
        try {
            //Print the files to console.
            Files.lines(Paths.get(path)).forEach(System.out::println);
        } catch (IOException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }
    }
}
