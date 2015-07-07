import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

/**
 * Created by bigwood928 on 3/26/14.
 */
public class BracketMadness {

    public static void main(String[] args) {
        File file = new File("./res/bracketinput");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = reader.readLine())!=null) {
                madness(line);
            }
        } catch (Exception e) {

        }
    }

    private static void madness(String line) {
        LinkedList<String> phraseStack = new LinkedList<String>();
        StringBuffer currentBlock = new StringBuffer();
        StringBuffer phrase = new StringBuffer();
        for(int i=0; i<line.length(); i++) {
            switch (line.charAt(i)) {
                case '{':
                case '[':
                case '(':
                    phraseStack.push(currentBlock.toString());
                    currentBlock = new StringBuffer();
                    break;
                case '}':
                case ']':
                case ')':
                    phrase.append(currentBlock);
                    currentBlock = new StringBuffer();
                    String previousBlock = phraseStack.pop();
                    if(!previousBlock.isEmpty()) {
                        previousBlock = previousBlock.trim();
                        previousBlock = ' ' + previousBlock;
                    }
                    currentBlock.append(previousBlock);
                    break;
                default:
                    currentBlock.append(line.charAt(i));
                    break;
            }
        }
        System.out.println(phrase.toString());
    }
}
