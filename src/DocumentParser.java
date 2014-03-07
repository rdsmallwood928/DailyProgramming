import java.io.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/19/14
 * Time: 7:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentParser {

    public static void main(String[] args) {
        File loremipsum = new File("./res/loremipsum");
        DocumentParser parser = new DocumentParser();
        parser.readFile(loremipsum);
    }

    public void readFile(File file){
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        HashMap<Character, Integer> letters =  new HashMap<Character, Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null) {
                StringBuilder builder = new StringBuilder();
                for(int i=0;i<line.length(); i++) {
                    if(line.charAt(i) == ' '){
                        Integer numWords = words.get(builder.toString());
                        if(numWords != null){
                            numWords++;
                        } else {
                            numWords = 1;
                        }
                        words.put(builder.toString(), numWords) ;
                    } else {
                        char current = line.charAt(i);
                        builder.append(current);
                        if(Character.isAlphabetic(current)) {
                            Integer numChars = letters.get(current);
                            if(numChars == null) {
                                numChars=0;
                            }
                            numChars++;
                            letters.put(current, numChars);
                        }
                    }
                }
            }
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
