package recursion;

import java.util.Scanner;

public class Factoral {

		private static int n;

		public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter n");
			int n = input.nextInt();
			System.out.println(factorial(n));
		}

		private static int factorial(int n2) {
			System.out.println(n);
			return (n == 1)? 1: n * factorial(n - 1);
		}
	}