import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class grep {


    public static void runGrep(String path, String term, Boolean sensitive) {
        try {
            Path input = Paths.get(path);
            BufferedReader reader = new BufferedReader(new FileReader(input.toFile()));
            String temp;
            while (reader.ready()) { //is searching for a case-sensitive word or term
                if (sensitive) {
                    temp = reader.readLine();
                    if (temp.contains(term))
                        System.out.println(temp);
                } else {
                    temp = reader.readLine();
                    if (temp.toLowerCase().contains(term.toLowerCase()))
                        System.out.println(temp);
                }
            }
        } catch (IOException ex) {
            System.err.println("Something went wrong... " + ex.getMessage());
        }
    }
}
