import java.util.Scanner;


public class Prime {
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		//check();
		File f = new File();
		f.method();
	}
	public static void check(){
		System.out.println("enter number");
		int x = input.nextInt();
		for(int i = 2; i<15; i ++){
			if(x%i == 0 && x != i){
				System.out.println(false);
				System.exit(0);
			}
		}

		System.out.println(true);
	}
}
