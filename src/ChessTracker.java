import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by bigwood928 on 3/26/14.
 */
public class ChessTracker {

    public static void main(String[] args) {
        File file = new File("./res/chessmoves");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = reader.readLine())!=null) {
                processMoves(line);
            }
        } catch (Exception e) {

        }
    }

    private static void processMoves(String line) {

    }

    public class Game {

        Piece white[] = new Piece[16];
        Piece black[] = new Piece[16];

        public Game() {
            for(int i=0; i<8; i++) {

            }
        }

    }

    public class Piece {
        PieceType type;
        String location;

        public Piece(PieceType type, String location) {
            this.type = type;
            this.location = location;
        }

    }

    public enum PieceType {
        KING,
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        PAWN;
    }
}
