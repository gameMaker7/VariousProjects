package recursion;

import java.util.Scanner;

public class fiv {

	private static Scanner input = new Scanner(System.in);
	private static int n;
	private static int total = 0;


	public static void main(String[] args) {
		System.out.println("enter N");
		int a = 0;
		int b = 1;
		n = input .nextInt();
		try{
			doom(a, b);
		}catch(StackOverflowError e){
			System.out.println("Number to Large");
		}
	}

	private static void doom(int a, int b) {
		if(total == n){
			System.out.println(b);
			return;
		}else{
			int c = b;
			b += a;
			a = c;
			total++;
			doom(a,b);
		}

	}

}
