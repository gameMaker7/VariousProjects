import java.util.ArrayList;

/**
 * Created by Denver on 2/3/2016.
 */

public class Piece{
    MoveTypes[] moves = new MoveTypes[3];
    String color;
    String abbreviation;
    String name;
    String location;
    boolean canCastle = false;
    ArrayList<String> moveSet = new ArrayList<>();
    boolean hasMoved = false;
    Piece(String color, String abbrev, String name, String Location){
        this.color = color;
        this.abbreviation = abbrev;
        this.name = name;
        this.location = Location;

    }

    public Board CreatePossibleMoves(String location, Board board) {
        return null;
    }

    protected boolean AddMove(String s, Board board) {
        char currentLetterValue = s.charAt(0);
        int currentNumberValue = Integer.valueOf(s.substring(1));
        if(currentLetterValue > 'h' || currentLetterValue < 'a' || currentNumberValue > 8 || currentNumberValue < 1){
            return false;
        }
        if(board.board.get(s) != null && board.board.get(s).color != color){
            moveSet.add(s);
            return false;
        }else if(board.board.get(s) == null){
            moveSet.add(s);
            return true;
        }else{
            return false;
        }
    }

    public boolean CheckPossibleMoves(String location2) {
        for (int i = 0; i < moveSet.size(); ++i){
            String s = moveSet.get(i);
            if(s.equals(location2)){
                return true;
            }
        }
        return false;
    }


    protected void LineMove(char currentLetterValue, int currentNumberValue, Board board) {
        // vertical
        int tmp = currentNumberValue;
        String s;
        while (tmp <= 8) {
            ++tmp;
            s = currentLetterValue + (tmp+"");
            if(!AddMove(s, board)){
                break;
            }
        }
        tmp = currentNumberValue;
        while (tmp >= 1) {
            --tmp;
            s = currentLetterValue + (tmp + "");
            if(!AddMove(s, board)){break;}
        }

        // horizontal
        char tmpc = currentLetterValue;
        while (tmpc <= 'h') {
            ++tmpc;
            s = tmpc + (currentNumberValue+"");
            if(!AddMove(s, board)){break;}
        }

        tmpc = currentLetterValue;
        while (tmpc >= 'a') {
            --tmpc;
            s = tmpc + (currentNumberValue+"");
            if(!AddMove(s, board)){break;}
        }
    }

    protected void DiagonalMove(char currentLetterValue, int currentNumberValue, Board board) {
        String s;
        int tmp = currentNumberValue;
        int tmpc = currentLetterValue;
        while (tmp <= 8 && tmpc <= 'h') {
            ++tmp;
            ++tmpc;
            s = (char)tmpc + (tmp+"");
            if(!AddMove(s, board)){
                break;
            }
        }
        tmp = currentNumberValue;
        tmpc = currentLetterValue;
        while (tmp <= 8 && tmpc >= 'a') {
            ++tmp;
            --tmpc;
            s = (char)tmpc + (tmp+"");
            if(!AddMove(s, board)){
                break;
            }
        }
        tmp = currentNumberValue;
        tmpc = currentLetterValue;
        while (tmp >= 1 && tmpc <= 'h') {
            --tmp;
            ++tmpc;
            s = (char)tmpc + (tmp+"");
            if(!AddMove(s, board)){
                break;
            }
        }
        tmp = currentNumberValue;
        tmpc = currentLetterValue;
        while (tmp >= 1 && tmpc >= 'a') {
            --tmp;
            --tmpc;
            s = (char)tmpc + (tmp+"");
            if(!AddMove(s, board)){
                break;
            }
        }
    }
}
