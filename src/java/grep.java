import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class grep {
    public static void main(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException("Usage: [document] document to search, [term] search term, [-s] case sensitive");
        try {
            File input = new File(args[0]);
            Scanner fileReader = new Scanner(input);
            String temp;
            while (fileReader.hasNextLine()) { //is searching for a case-sensitive word or term
                if (args.length > 2 && args[2].equalsIgnoreCase("-s")) {
                    temp = fileReader.nextLine();
                    if (temp.contains(args[1]))
                        System.out.println(temp);
                } else if (args.length == 2) { //Default case, just find matching words/terms
                    temp = fileReader.nextLine();
                    if (temp.toLowerCase().contains(args[1].toLowerCase()))
                        System.out.println(temp);
                } else
                    throw new IllegalArgumentException("Usage: [document] document to search, [term] search term, [-s] case sensitive"); //the user made did not provide the correct arguments
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong... " + ex.getMessage());
        }
    }
}
