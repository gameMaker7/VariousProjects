/**
 * Created by Denver on 2/5/2016.
 */
public class Knight extends Piece {
    Knight(String color, String abbrev, String piece, String s) {
        super(color, abbrev, piece, s);
        moves[0] = MoveTypes.Knight;
    }
    private void KnightMove(char k, int n, Board board) {
        String s;
        //far
        s = ((char)(k+1)) + ((n + 2) + "");
        AddMove(s, board);
        s = ((char)(k-1)) + (n + 2 + "");
        AddMove(s, board);
        s = ((char)(k+1)) + (n - 2 + "");
        AddMove(s, board);
        s = ((char)(k-1)) + (n - 2 + "");
        AddMove(s, board);
        //wide
        s = ((char)(k-2))+(n+1+"");
        AddMove(s, board);
        s = ((char)(k+2))+(n+1+"");
        AddMove(s, board);
        s = ((char)(k-2))+(n-1+"");
        AddMove(s, board);
        s = ((char)(k+2))+(n-1+"");
        AddMove(s, board);
    }

    @Override
    public Board CreatePossibleMoves(String location, Board board) {
        char currentLetterValue = location.charAt(0);
        int currentNumberValue = Integer.valueOf(location.substring(1));
        KnightMove(currentLetterValue, currentNumberValue, board);
        return board;
    }
}
