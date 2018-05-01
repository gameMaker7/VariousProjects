/**
 * Created by Denver on 2/5/2016.
 */
public class Rook extends Piece {
    Rook(String color, String abbrev, String piece, String s) {
        super(color, abbrev, piece, s);
        moves[0] = MoveTypes.InfiniteLine;
        moves[1] = MoveTypes.Castle;
        canCastle = true;
    }

    @Override
    public Board CreatePossibleMoves(String location, Board board) {
        char currentLetterValue = location.charAt(0);
        int currentNumberValue = Integer.valueOf(location.substring(1));
        for (int i = 0; i < moves.length; ++i){
            if(moves[i] != null) {
                String s;
                switch (moves[i]) {
                    case InfiniteLine:
                        LineMove(currentLetterValue, currentNumberValue, board);
                        break;
                    case Castle:
                        RookCastling(currentLetterValue, currentNumberValue, board);
                        break;
                    default:
                        break;
                }
            }
        }
        return board;
    }

    private void RookCastling(char currentLetterValue, int currentNumberValue, Board board) {
        if (!hasMoved) {
            if (currentLetterValue == 'a' && currentNumberValue == 1) { // c1
                if(CheckPossibleMoves("b1")){
                    canCastle = true;
                    AddMove("c1", board);
                }
            } else if (currentLetterValue == 'a' && currentNumberValue == 8) { // d8
                if(CheckPossibleMoves("b8") && CheckPossibleMoves("c8")){
                    canCastle = true;
                    AddMove("d8", board);
                }
            } else if (currentLetterValue == 'h' && currentNumberValue == 1) { // e1
                if(CheckPossibleMoves("g1") && CheckPossibleMoves("f1")){
                    canCastle = true;
                    AddMove("e1", board);
                }
            } else if (currentLetterValue == 'h' && currentNumberValue == 8) { // f8
                if(CheckPossibleMoves("g8")){
                    canCastle = true;
                    AddMove("f8", board);
                }
            } else {
                canCastle = false;
            }
        }
    }
}
