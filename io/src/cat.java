import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class cat {

    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Usage: [path] path to file");
        try {
            File file = new File(args[0]);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                System.out.println(fileReader.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }

    }
}
