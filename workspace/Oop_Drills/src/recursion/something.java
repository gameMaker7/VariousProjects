package recursion;

import java.util.Scanner;

public class something {
	private static int n;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter n");
		int n = input.nextInt();
		System.out.println(sum(n));
	}

	private static int sum(int n2) {
	
		return n == 1? 1: n + sum(n -1);
	}
}
