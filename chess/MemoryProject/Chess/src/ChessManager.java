import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Denver on 2/12/2016.
 */
public class ChessManager {

    FileLoader commandLoader = new FileLoader();
    ArrayList<Piece> validPieces = new ArrayList<>();
    Board board = new Board();
    Scanner input = new Scanner(System.in);
    boolean isWhiteTurn = true;
    public static void main(String[] args) {
        ChessManager c = new ChessManager();
        c.LoadFile("module1test.txt");
        c.RegexRefinment();
        try {


            while (true) {
                c.board.DisplayBoard();
                c.GetCommand();
            }
        }finally {
            c.input.close();
        }
    }

    private void GetCommand() {
        Piece p = null;
        int pi = 0;
        if (isWhiteTurn) {
            System.out.print("White ");
        } else {
            System.out.print("Black ");
        }
        System.out.println("turn please choose a piece to move");
        System.out.println("0) Quit");
        CollectValidPieces();
        try {
            int i = input.nextInt();
            if (i == 0) {
                System.exit(0);
            }
            if (i > validPieces.size() || i < 0) {
                System.out.println("Invalid Selection.");
            } else {
                p = validPieces.get(i - 1);
                ListPossibleMoves(p);
                pi = input.nextInt();
                if (pi != 0) {
                    String s = p.moveSet.get(pi - 1);
                    if (board.board.get(s) != null) {
                        s = s.concat("*");
                        System.out.println(s);
                    } else {
                        ParseCommand(p.location + " " + s);
                    }
                }
            }
                }catch(InputMismatchException e){
                    System.out.println("Please enter a number other formats are not appreciated");
                    input.next();
                }
        }

    private void ListPossibleMoves(Piece p) {
        System.out.println("0) Go Back");
        for (int i = 0; i < p.moveSet.size(); ++i){
            String s = p.moveSet.get(i);
            System.out.println((i+1) + ") Move to: " + s);
        }
    }

    private void CollectValidPieces() {
        validPieces.clear();
        for(String s: board.board.keySet()){
            if(board.board.get(s) != null){
                Piece p = board.board.get(s);
                if(isWhiteTurn && p.color == "White"){
                    CreateMoveSets(p, s);
                    if(p.moveSet.size() != 0){
                        validPieces.add(p);
                    }
                }
                if(!isWhiteTurn && p.color == "Black"){
                    CreateMoveSets(p, s);
                    if(p.moveSet.size() != 0){
                        validPieces.add(p);
                    }
                }
            }
        }
        for(int i = 1; i <= validPieces.size(); ++i){
            ShowPiece(validPieces.get(i-1).location, validPieces.get(i-1), i);
        }
    }

    private void ShowPiece(String s, Piece p, int i) {
        System.out.println( i + ") " + p.name + " on " + s);
    }

    private void CreateMoveSets(Piece p, String s) {
        if(p == null){
            return;
        }else{
            p.moveSet.clear();
            board = p.CreatePossibleMoves(s, board);
            }
    }

    private void LoadFile(String s) {
        commandLoader.LoadFile(s);
    }

    //-------------------------------------------------------------------------------
    // -Movement, Placement, Capturing, etc. -
    //-------------------------------------------------------------------------------

    private void Movement(String locationx1, String locationy1, String locationx2, String locationy2) {
        // clear move set

        Piece p = board.board.get(locationx1+locationy1);
        // create possible moves for piece and check if team is correct
        if(RulesManager.ValidMove(locationx1+locationy1, locationx2+locationy2, board, false)
                && CheckTeam(board.board.get(locationx1+locationy1), isWhiteTurn)){
            System.out.println("Move piece from " + locationx1 + locationy1 + " to " + locationx2 + locationy2);
            // Movement changes
            p.hasMoved = true;
            board.board.put(locationx2+locationy2, p);
            board.board.replace(locationx1+locationy1, null);
            p.location = locationx2+locationy2;
            isWhiteTurn = !isWhiteTurn;
        }else{
            System.out.println(" -- Move Invalid: " + locationx1+locationy1 + " " + locationx2+locationy2);
        }
    }

    private boolean CheckTeam(Piece piece, boolean isWhiteTurn) {

        String s;
        if(isWhiteTurn){
            s = "White";
        }else{
            s = "Black";
        }
        if(s.equals(piece.color)){
         return true;
        }else{
            System.out.print("Wrong Team");
            return false;
        }
    }

    private void Placement(String piece, String color, String locationx, String locationy) {
        String tmp = color;
        switch (color){
            case "l": color = "White";
                break;
            case "d": color = "Black";
                break;
        }

        switch (piece){
            case "p": piece = "Pawn";
                board.board.put(locationx+locationy, new Pawn(color, "p" + tmp, piece, locationx+locationy));
                break;
            case "r": piece = "Rook";
                board.board.put(locationx+locationy, new Rook(color, "r" + tmp, piece, locationx+locationy));
                break;
            case "n": piece = "Knight";
                board.board.put(locationx+locationy, new Knight(color, "n" + tmp, piece, locationx+locationy));
                break;
            case "b": piece = "Bishop";
                board.board.put(locationx+locationy, new Bishop(color, "b" + tmp, piece, locationx+locationy));
                break;
            case "q": piece = "Queen";
                board.board.put(locationx+locationy, new Queen(color, "q" + tmp, piece, locationx+locationy));
                break;
            case "k": piece = "King";
                board.board.put(locationx+locationy, new King(color, "k"+tmp, piece, locationx+locationy));
                break;
        }
        System.out.println("Place " + color + " " + piece + " on " + locationx + locationy);
    }

    private void Capture(String locationx1, String locationy1, String locationx2, String locationy2) {
        // clear move set
        Piece p = board.board.get(locationx1+locationy1);
        // create possible moves for piece and check if team is correct
        if(RulesManager.ValidMove(locationx1+locationy1, locationx2+locationy2, board, true)
                && CheckTeam(board.board.get(locationx1+locationy1), isWhiteTurn)){
            System.out.println("Move piece from " + locationx1 + locationy1 + " to " + locationx2 + locationy2 +
                    " Capture piece at this location.");
            p.hasMoved = true;
            board.board.put(locationx2+locationy2, p);
            board.board.replace(locationx1+locationy1, null);
            p.location = locationx2+locationy2;
            isWhiteTurn = !isWhiteTurn;
        }else{
            System.out.println(" -- Move Invalid: " + locationx1+locationy1 + " " + locationx2+locationy2 + "*");
        }
    }

    private void Castle(String locationx1, String locationy1, String locationx2, String locationy2,
                        String locationx3, String locationy3, String locationx4, String locationy4)
    {
        Piece p1 = board.board.get(locationx1+locationy1);
        CreateMoveSets(p1, locationx1+locationy1);
        Piece p2 = board.board.get(locationx3+locationy3);
        CreateMoveSets(p2, locationx3+locationy3);

        if(RulesManager.ValidMove(locationx1+locationy1, locationx2+locationy2, board, false)
                && CheckTeam(board.board.get(locationx1+locationy1), isWhiteTurn)) {
            if (RulesManager.ValidMove(locationx3 + locationy3, locationx4 + locationy4, board, false)
                    && CheckTeam(board.board.get(locationx1 + locationy1), isWhiteTurn)) {

                p1.hasMoved = true;
                board.board.put(locationx2 + locationy2, board.board.get(locationx1 + locationy1));
                board.board.replace(locationx1 + locationy1, null);
                p1.location = locationx2+locationy2;
                p1.canCastle = false;

                p2.hasMoved = true;
                board.board.put(locationx4 + locationy4, board.board.get(locationx3 + locationy3));
                board.board.replace(locationx3 + locationy3, null);
                p2.location = locationx4+locationy4;
                p2.canCastle = false;

                isWhiteTurn = !isWhiteTurn;
                System.out.println("Move piece from " + locationx1 + locationy1 + " to " + locationx2 + locationy2 +
                        " Move piece from " + locationx3 + locationy3 + " to " + locationx4 + locationy4);
            }
        }else{
            System.out.println("Castling Rejected: " + locationx1 + locationy1 + " to " + locationx2 + locationy2 + " " + locationx3 + locationy3 + " to " + locationx4 + locationy4);
        }
    }

    //-------------------------------------------------------------------------------
    // - Regex Stuff -
    //-------------------------------------------------------------------------------
    private void RegexRefinment() {
        ArrayList<String> commands = commandLoader.commands;
        for(int i = 0; i < commands.size(); ++i) {

            // String to be scanned to find the pattern.
            String line = commands.get(i);
            ParseCommand(line);
        }
    }
    private void ParseCommand(String line) {
        String placePattern = "([rnbqkp])([ld])([abcdefgh])([12345678])";
        String movePattern = "([abcdefgh])([12345678])\\s([abcdefgh])([12345678])";
        String capturePattern = "([abcdefgh])([12345678])\\s([abcdefgh])([12345678])([*])";
        String castlePattern = "([abcdefgh])([12345678])\\s([abcdefgh])([12345678])\\s([abcdefgh])([12345678])\\s([abcdefgh])([12345678])";

        // Create a Pattern object
        Pattern place = Pattern.compile(placePattern);
        Pattern move = Pattern.compile(movePattern);
        Pattern capture = Pattern.compile(capturePattern);
        Pattern castle = Pattern.compile(castlePattern);

        // create matcher object.
        Matcher pp = place.matcher(line);
        Matcher mp = move.matcher(line);
        Matcher cp = capture.matcher(line);
        Matcher cap = castle.matcher(line);
        // checl for matches
        if (cap.find( )) {
            Castle(cap.group(1), cap.group(2), cap.group(3), cap.group(4), cap.group(5), cap.group(6), cap.group(7), cap.group(8));
        }
        else if(cp.find()){
            Capture(cp.group(1), cp.group(2), cp.group(3), cp.group(4));
        }
        else if(pp.find()){
            Placement(pp.group(1), pp.group(2), pp.group(3), pp.group(4));
        }
        else if(mp.find()){
            Movement(mp.group(1), mp.group(2), mp.group(3), mp.group(4));
        }
        else{
            System.out.println("ILLEGAL EXPRESSION");
        }
    }
}