/**
 * Created by Denver on 2/17/2016.
 */
public class RulesManager {


    public static boolean ValidMove(String location1, String location2, Board board, boolean capture){

        // piece exists
        if(board.board.get(location1) == null) {
            System.out.print("No piece at this location: " + location1);
            return false;
        }
        // Capture command unacceptable
        if(capture && board.board.get(location2) == null){
            System.out.print("Capture command accepted, no piece at location: " + location2 + " Capture command rejected.");
            return false;
        }
        // does a piece occupy space
        if(board.board.get(location2) != null) {
            if(!capture){
                System.out.print("Not Capturing, however a piece exists in this location: " + location2);
                return false;}
            if(CheckColor(location1, location2, board)){
            }else{
                return false;
            }
        }
        // Check Legality of Command
        return CheckMove(location1, location2, board);
    }

    private static boolean CheckMove(String location1, String location2, Board board) {

        Piece p = board.board.get(location1);
        return p.CheckPossibleMoves(location2);
    }


    private static boolean CheckColor(String location1, String location2, Board board) {
        String pieceColor1 = board.board.get(location1).color;
        String pieceColor2 = board.board.get(location2).color;
        if(pieceColor1 == pieceColor2){
            System.out.print("Same Color Piece Detected. INVALID MOVEMENT!");
            return false;
        }else{return true;}
    }
}








