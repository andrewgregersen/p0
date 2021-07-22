import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class cat {

    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Usage: [path]");
        try {

            //Import the file into the program
            File file = new File(args[0]);
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
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
        } catch (IOException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }

    }
}
