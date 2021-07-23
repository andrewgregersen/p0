import java.nio.file.InvalidPathException;
import java.util.Scanner;


/**
 * Main class to make the program. Uses threads for running the users commands and then waits for those commands to finish
 * before continuing.
 */
public class ASH {


    public static void main(String[] args) {

        SHDriver driver = new SHDriver();
        String input;
        try (Scanner stdin = new Scanner(System.in)) {
            while (true) { //have the program run until the user exits the shell
                System.out.print("$:> ");
                input = stdin.nextLine();
                if (input.toLowerCase().startsWith("exit"))//check to see if the user wants to exit the shell
                    break;
                else if (input.toLowerCase().startsWith("cd")) //check to see if the user wants to change their directory
                    driver.setCwd(input);
                else if (input.toLowerCase().startsWith("pwd")) //tell console to print the current working directory
                    driver.printWorkingDirectory();
                else if (input.toLowerCase().startsWith("cat"))
                    driver.runCat(input);
                else if (input.toLowerCase().startsWith("grep"))
                    driver.runGrep(input);
                else if (input.toLowerCase().startsWith("analyze"))
                    driver.runAnalyzer(input);
                else if (input.toLowerCase().startsWith("ls"))
                    driver.ls(input);
                else driver.runOther(input);

            }
        } catch (InvalidPathException ex) {
            System.err.println("Something went wrong: " + ex.getMessage());
        } catch (ErrException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
