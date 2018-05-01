import java.util.Scanner;


public class caseChange {


	final static int ALPHA = 26;
	final static int KEY = 13;


	public static void main(String[] args) {

		int moveUp = 0;
		Scanner input = new Scanner(System.in);

		System.out.println("Type");
		String action = input.nextLine();
		int actionLegnth = action.length();
		char[] encode = new char[action.length()];
		for(int j = 0; j<encode.length; j ++){
			encode[j] = action.charAt(j);
		}

		for(int i = 0 ; i < actionLegnth; i++){

			char code = action.charAt(moveUp);
			if(code == ' '){
				System.out.print(" ");
				moveUp++;
				continue;
			}

			int key = (code - 'a'- 32);
			char unlocked = (char) (key + 'a');
			encode[i] = unlocked;
			moveUp++;
			System.out.print(encode[i]);
		}
		input.close();
	}
}
