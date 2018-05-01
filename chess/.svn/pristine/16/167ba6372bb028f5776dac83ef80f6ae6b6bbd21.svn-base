/**
 * Created by Denver on 2/5/2016.
 */
public class Bishop extends Piece {
    Bishop(String color, String abbrev, String piece, String s) {
        super(color, abbrev, piece, s);
        moves[0] = MoveTypes.InfiniteDiagonal;
    }
    @Override
    public Board CreatePossibleMoves(String location, Board board) {
        char currentLetterValue = location.charAt(0);
        int currentNumberValue = Integer.valueOf(location.substring(1));
        DiagonalMove(currentLetterValue, currentNumberValue, board);
        return board;
    }
}
