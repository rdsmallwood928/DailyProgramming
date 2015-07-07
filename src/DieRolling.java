import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by bigwood928 on 5/21/14.
 */
public class DieRolling {


    public static void main(String[] args) {
        int numRolls =  1000000;
        Long[] distribution = {0l,0l,0l,0l,0l,0l};
        System.out.println("# of Rolls 1s     2s     3s     4s     5s     6s");
        System.out.println("====================================================");
        DecimalFormat decimalFormat = new DecimalFormat("00.000");
        char[] rollString = new char[10];

        for(int i=0; i<numRolls; i++) {
            Die die = new Die(6);
            long roll = die.roll();
            distribution[(int)roll]++;
            double log = Math.log10(i);
            if(log == (int)log && i != 1) {
                for(int j=0; j<rollString.length; j++) {
                    rollString[j] = ' ';
                }
                StringBuilder builder = new StringBuilder();
                String tempRollString = Integer.toString(i);
                for(int j=0; j<tempRollString.length(); j++) {
                    rollString[j] = tempRollString.charAt(j);
                }
                builder.append(rollString);
                for(int j=0;j<distribution.length; j++) {
                   builder.append(" ").append(decimalFormat.format(((double)distribution[j]/(double)i)*100)).append('%');
                }
                System.out.println(builder.toString());
            }

        }

    }

    public static class Die {

        long numSides = 6;

        public Die(int numSides) {
            this.numSides = numSides;
        }

        public long roll() {
            Random random = new Random();
            return (long)Math.abs(random.nextLong()%numSides);
        }
    }
}
