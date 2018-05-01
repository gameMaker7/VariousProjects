import java.util.HashMap;

/**
 * Created by Denver on 2/3/2016.
 */
public class Board {
   HashMap<String, Piece> board = new HashMap<>();
   private static final int FIELD = 8;
   private static final char[] LETTERS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

   Board(){
      for(int i = 1; i <=FIELD; ++i){
         for(int a = 1; a<=LETTERS.length; ++a){
            String val = "" + LETTERS[a-1]+i;
            board.put(val, null);
         }
      }
   }

   public void DisplayBoard() {
      System.out.println("---------------------------------------------------------------");
      for(int i = 1; i <=FIELD; ++i){
         System.out.print(i + " ");
         for(int a = 1; a<= LETTERS.length; ++a){
            String val = "" + LETTERS[a-1]+i;
            Piece tmp =  board.get(val);
            if(tmp != null){ System.out.print("|" + tmp.abbreviation + "");}
            else{System.out.print("|--");}
         }
         System.out.println("|\n");
      }
      System.out.println("   A  B  C  D  E  F  G  H");
      System.out.println("---------------------------------------------------------------");
   }
}
