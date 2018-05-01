/**
 * Created by Denver on 2/5/2016.
 */
public class Queen extends Piece {
    Queen(String color, String abbrev, String piece, String s) {
        super(color, abbrev, piece, s);
        moves[0] = MoveTypes.InfiniteDiagonal;
        moves[1] = MoveTypes.InfiniteLine;
    }

    @Override
    public Board CreatePossibleMoves(String location, Board board) {
        char currentLetterValue = location.charAt(0);
        int currentNumberValue = Integer.valueOf(location.substring(1));
        for (int i = 0; i < moves.length; ++i) {
            if (moves[i] != null) {
                String s;
                switch (moves[i]) {
                    case InfiniteLine:
                        LineMove(currentLetterValue, currentNumberValue, board);
                        break;
                    case InfiniteDiagonal:
                        DiagonalMove(currentLetterValue, currentNumberValue, board);
                        break;
                    default:
                        break;
                }
            }
        }
        return board;
    }
}