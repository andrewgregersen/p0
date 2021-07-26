import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;


/**
 * Main class to make the program. Uses threads for running the users commands and then waits for those commands to finish
 * before continuing.
 */
public class ASH {


    public static void main(String[] args) {

        SHDriver driver = new SHDriver();
        String input;
        try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) { //have the program run until the user exits the shell
                System.out.print("$:> ");
                input = stdin.readLine();
                if (input.toLowerCase().startsWith("exit"))//check to see if the user wants to exit the shell
                    break;
                else if (input.toLowerCase().startsWith("cd")) //check to see if the user wants to change their directory
                    driver.setCwd(input);
                else if (input.toLowerCase().startsWith("pwd")) //tell console to print the current working directory
                    driver.printWorkingDirectory();
                else if (input.toLowerCase().startsWith("cat"))
                    driver.runCat(input);
                else if (input.toLowerCase().startsWith("grep"))
                    driver.runGrep();
                else if (input.toLowerCase().startsWith("analyze"))
                    driver.runAnalyzer(input);
                else if (input.toLowerCase().startsWith("ls"))
                    driver.ls(input);
                else driver.runOther(input);
            }
        } catch (InvalidPathException | FileNotFoundException ex) {
            System.err.println("Something went wrong: " + ex.getMessage());
        } catch (UsageException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
