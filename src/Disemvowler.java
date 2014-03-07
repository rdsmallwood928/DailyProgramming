import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by bigwood928 on 2/25/14.
 */
public class Disemvowler {

    public static void main(String[] args){
        File file = new File("./res/disemvowlerinput");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = reader.readLine())!=null) {
                Disemvowler.disemvowel(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void disemvowel(String line) {
        StringBuilder noVowels = new StringBuilder();
        StringBuilder vowels = new StringBuilder();
        for(int i=0; i<line.length();i++) {
            char letter = line.charAt(i);
            if(isVowel(letter)) {
                vowels.append(letter);
            } else if(letter!=' '){
                noVowels.append(letter);
            }
        }
        System.out.println(noVowels.toString());
        System.out.println(vowels.toString());
/*        File file = new File("./res/emvowlerinput");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(noVowels.toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(vowels.toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static boolean isVowel(char letter) {
        String string = new String();
        string = string+letter;
        Pattern pattern = Pattern.compile("[aeiouAEIOU]");
        return pattern.matcher(string).matches();
    }

}
