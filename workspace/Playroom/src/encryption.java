import java.util.Random;
import java.util.Scanner;


public class encryption {


	final static int ALPHA = 26;
	final static int KEY = 13;


	public static void main(String[] args) {

		Random gen = new Random();
		Scanner input = new Scanner(System.in);

		System.out.println("how big is the code?");
		int action = input.nextInt();
		char[] encode = {'a','b','c','d','f','e','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		String truecode = "";

		for(int i = 0 ; i < action; i++){

			char code = encode[gen.nextInt(encode.length)];
			truecode += Character.toString(code);
			
			int key = (code - 'a'+ KEY) % ALPHA;
			char unlocked = (char) (key + 'a');
			System.out.print(unlocked);
		}
		System.out.println();
		System.out.println("what is the code");
		String check = input.next();
		if(check.equals(truecode)){
			System.out.println("nice");
		}

		input.close();
	}
}
