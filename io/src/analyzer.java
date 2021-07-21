import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class analyzer {

    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Usage: [path] path to file");
        try {
            File file = new File(args[0]);
            Scanner fileReader = new Scanner(file);
            Map<String, Integer> termMap = new TreeMap<String, Integer>();
            String temp;
            while (fileReader.hasNext()) {
                temp = fileReader.next();
                if (termMap.putIfAbsent(temp, 1) != null)
                    termMap.put(temp, termMap.get(temp) + 1);
            }
            System.out.println("Term : Occurrences");
            Set<String> keys = termMap.keySet();
            for (String key : keys) {
                System.out.println(key + " : " + termMap.get(key));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }

    }
}
