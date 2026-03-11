import java.io.*;
import java.util.*;

public class Anagrams {

    //Returns the "signature" of a word: its characters sorted alphabetically.
    // e.g. "reader" -> "adeerr", "dearer" -> "adeerr"
    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) throws IOException {

        // ---------------------------------------------------------------
        // 1. Accept input filename from command line (like the Python code)
        // ---------------------------------------------------------------
        String inputFile;
        if (args.length == 1) {
            inputFile = args[0];
            System.out.println("Data file: " + inputFile);
        } else {
            // Default to ulysses.text if no argument given
            inputFile = "ulysses.text";
            System.out.println("No argument given. Defaulting to: " + inputFile);
        }

        // ---------------------------------------------------------------
        // 2. Read file and build word-frequency map D
        // Strip punctuation but keep apostrophes; lowercase everything.
        // ---------------------------------------------------------------
        HashMap<String, Integer> D = new HashMap<>();

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(inputFile), "ISO-8859-1")
        );

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            String[] words = line.split("\\s+");
            for (String w : words) {
                if (w.isEmpty()) continue;
                // Strip leading/trailing punctuation (keep apostrophes inside words)
                // Mirrors Python: w.strip('[0123456789(,.,.;:_.!?---)]')
                w = w.replaceAll("^[^a-zA-Z']+|[^a-zA-Z']+$", "");
                // Lowercase
                w = w.toLowerCase();
                if (w.isEmpty()) continue;
                // Count word frequency in D
                if (D.containsKey(w)) {
                    D.put(w, D.get(w) + 1);
                } else {
                    D.put(w, 1);
                }
            }
        }
        reader.close();
        System.out.println("Lines read: " + lineNumber);
        System.out.println("Unique words found: " + D.size());
