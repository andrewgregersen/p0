import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class cat {

    public static void printDocument(String path) {
        try {
            //Import the file into the program
            Path file = Paths.get(path).toAbsolutePath().normalize();
            BufferedReader fileReader = new BufferedReader(new FileReader(file.toFile()));
            int counter = 1;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //Start printing the file to the console.
            while (fileReader.ready()) {
                if (counter % 30 == 0) {
                    System.out.print("----------Press Any Key to Continue----------"); //Need to make it accept enter
//                    while (!KeyStroke.getKeyStroke('q').isOnKeyRelease()) {
//                    }
                    input.readLine();
                }
                System.out.println(fileReader.readLine());
                counter++;
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }
    }
}

