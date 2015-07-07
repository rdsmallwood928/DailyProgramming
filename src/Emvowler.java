import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by bigwood928 on 2/26/14.
 */
public class Emvowler{

    public Set<String> words = new HashSet<String>();

    public static void main(String[] args) throws IOException {
        Emvowler emvowler = new Emvowler();
        File input = new File("./res/emvowlerinput");

        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(input));
            String line = "";

            while((line = inputStream.readLine()) != null) {
                String nonVowels = line;
                String vowels = inputStream.readLine();
                emvowler.emvowel(nonVowels, vowels);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void emvowel(String nonVowels, String vowel) {
        Set<String> words = findWords(nonVowels, vowel);
    }

    private Set<String> findWords(String nonVowels, String vowel) {

        return null;
    }

    public Emvowler() {
        File wordBankFile = new File("./res/wordBank");
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(wordBankFile));
            String newWord = "";
            while((newWord = inputStream.readLine()) != null){
                newWord = newWord.toLowerCase();
                words.add(newWord);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Trie {
        //Node

        public class Node{
            public String data;
            public Node parent;
            public List<Node> children;

            public Node(String data, Node parent) {

            }



        }
    }


}
