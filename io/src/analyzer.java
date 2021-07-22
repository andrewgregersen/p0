import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class analyzer {

    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Usage: [path] path to file");
        try {
            Map<String, Integer> termMap = getTerms(args[0]);
            System.out.println("Term : Occurrences");
            Set<String> keys = termMap.keySet();
            for (String key : keys) {
                if (key.length() != 1)
                    System.out.println(key + " : " + termMap.get(key));
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }

    }


    /**
     * @param path: The path to the file that is to be read
     * @return A map o keys and the number of times that key occurs
     * @throws IOException The file does not exist or an error occurs in reading the file
     */
    private static Map<String, Integer> getTerms(String path) throws IOException {
        File file = new File(path);
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        Map<String, Integer> termMap = new TreeMap<>();
        String temp;
        while (fileReader.ready()) {
            temp = fileReader.readLine();
            for (String s : temp.split(" ")) {
                if (termMap.putIfAbsent(s, 1) != null)
                    termMap.put(s, termMap.get(s) + 1);
            }
        }
        fileReader.close();
        return termMap;
    }
}
