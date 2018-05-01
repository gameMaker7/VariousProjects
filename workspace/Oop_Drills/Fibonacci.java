package sim;

import java.util.Scanner;


public class Fibonacci {
	
	class KeyValue {
		int key;
		int value;
		
		KeyValue(int key, int value) {
			this.key = key;
			this.value = value;
		}
		
		int getKey() {
			return key;
		}
		
		int getValue() {
			return value;
		}
	}
	
	KeyValue lower = new KeyValue(0,0);
	KeyValue upper = new KeyValue(1,1);
	
	public int getNthValue(int n) {
		int value = 0;
		if (n == lower.getKey()) value = lower.getValue();
		else if (n == upper.getKey()) value = upper.getValue();
		else {
			value = getNthValue(n-2) + getNthValue(n-2);
			lower = upper;
			upper = new KeyValue(n, value);
		}
		return value;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter N => ");
		int n = scan.nextInt();
		Fibonacci fib = new Fibonacci();
		System.out.println(fib.getNthValue(n));
		scan.close();
	}
}
