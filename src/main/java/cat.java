import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class cat {

    public void printDocument(String path) {
        try {
            //Import the file into the program
            Path file = Paths.get(path).toAbsolutePath().normalize();
            BufferedReader fileReader = new BufferedReader(new FileReader(file.toFile()));
            int counter = 1;
            Scanner input = new Scanner(System.in);
            //Start printing the file to the console.
            while (fileReader.ready()) {
                if (counter % 30 == 0) {
                    System.out.println("----------Press Any Key to Continue----------"); //Need to make it acept enter
//                    while (!KeyStroke.getKeyStroke('q').isOnKeyRelease()) {
//                    }
                    input.next("\n");
                }
                System.out.println(fileReader.readLine());
                counter++;
            }
            input.close();
            fileReader.close();
        } catch (
                IOException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }
    }
}

