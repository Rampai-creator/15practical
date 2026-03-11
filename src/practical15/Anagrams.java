//Nakedi Rampai 4343349

import java.io.*;
import java.util.*;

public class Anagrams {

    //Returns the "signature" of a word: its characters sorted alphabetically.
    //e.g. "reader" -> "adeerr", "dearer" -> "adeerr"
    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) throws IOException {

        // 1. Accept input filename from command line 
        String inputFile;
        if (args.length == 1) {
            inputFile = args[0];
            System.out.println("Data file: " + inputFile);
        } else {
            //Default to ulysses.text if no argument given
            inputFile = "ulysses.text";
            System.out.println("No argument given. Defaulting to: " + inputFile);
        }

        //2. Read file and build word-frequency map D
        //Strip punctuation but keep apostrophes; lowercase everything.
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
                //Strip leading/trailing punctuation (keep apostrophes inside words)
                //Mirrors Python: w.strip('[0123456789(,.,.;:_.!?---)]')
                w = w.replaceAll("^[^a-zA-Z']+|[^a-zA-Z']+$", "");
                //Lowercase
                w = w.toLowerCase();
                if (w.isEmpty()) continue;
                //Count word frequency in D
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
        
        //3. Build anagram dictionary A: signature -> list of words
       
        HashMap<String, ArrayList<String>> A = new HashMap<>();

        for (String w : D.keySet()) {
            String sig = signature(w);
            if (!A.containsKey(sig)) {
                ArrayList<String> list = new ArrayList<>();
                list.add(w);
                A.put(sig, list);
            } else {
                A.get(sig).add(w);
            }
        }

        System.out.println("Signature groups: " + A.size());
        
        //4. Build anagram lines with rotations for groups of size >= 2
        //Each group produces N lines where N = number of words,
        //each line being a rotation of the word list.
    
        ArrayList<String> anagramLines = new ArrayList<>();

        for (String key : A.keySet()) {
            ArrayList<String> group = A.get(key);
            if (group.size() > 1) {
                //Build the initial anagram list string
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < group.size(); i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(group.get(i));
                }
                String anagramList = sb.toString();

                //First rotation 
                anagramLines.add(anagramList + "\\\\");

                //Subsequent rotations: move first word to end
                for (int repeat = 0; repeat < group.size() - 1; repeat++) {
                    int space = anagramList.indexOf(' ');
                    anagramList = anagramList.substring(space + 1) + " " + anagramList.substring(0, space);
                    anagramLines.add(anagramList + "\\\\");
                }
            }
        }

        System.out.println("Anagram lines generated: " + anagramLines.size());


