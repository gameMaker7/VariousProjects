/**
 * Created by Denver on 2/5/2016.
 */
class Pawn extends Piece {

    Pawn(String color, String abbrev, String piece, String s) {
        super(color, abbrev, piece, s);
        moves[2] = MoveTypes.PawnMove;
     }
    @Override
    public Board CreatePossibleMoves(String location, Board board) {
        char currentLetterValue = location.charAt(0);
        int currentNumberValue = Integer.valueOf(location.substring(1));
        PawnMoves(currentLetterValue, currentNumberValue, board);
        return board;
    }

    private void PawnMoves(char currentLetterValue, int currentNumberValue, Board board) {
        String s;
        // capture
        if(color == "Black"){
            s = ((char)(currentLetterValue-1)) + ((currentNumberValue-1)+"");
            if(board.board.get(s) != null){
            AddMove(s, board);
            }
            s = ((char)(currentLetterValue+1)) + ((currentNumberValue-1)+"");
            if(board.board.get(s) != null){
                AddMove(s, board);
            }        }
        else{
            s = ((char)(currentLetterValue+1)) + ((currentNumberValue+1)+"");
            if(board.board.get(s) != null){
                AddMove(s, board);
            }
            s = ((char)(currentLetterValue-1)) + ((currentNumberValue+1)+"");
            if(board.board.get(s) != null){
                AddMove(s, board);
            }
        }
        // double
        if(!hasMoved && color == "Black"){
            s = (currentLetterValue) + ((currentNumberValue-2)+"");
            AddMove(s, board);
        }
        if(!hasMoved && color == "White"){
            s = (currentLetterValue) + ((currentNumberValue+2)+"");
            AddMove(s, board);
        }
        // single
        if(color == "Black"){
            s = (currentLetterValue) + ((currentNumberValue-1)+"");
            AddMove(s, board);
        }
        if(color == "White"){
            s = (currentLetterValue) + ((currentNumberValue+1)+"");
            AddMove(s, board);
        }
    }
}
