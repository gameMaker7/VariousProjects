package as;


import java.util.Scanner;

public class solution {

	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Enter in form of x,y,z");
		String numbers = input.nextLine();
		float x = 0;
		float y = 0;
		float z = 0;
		int t = 1;
		numbers.replaceAll(" ", "");
		String[] array = numbers.split(",");
		x = Integer.parseInt(array[0]);
		y = Integer.parseInt(array[1]);
		if(array[2].contains(".")){
			array[2].replace(".", "");
			z = 10/Integer.parseInt(array[2]);
		}else{
			z = Integer.parseInt(array[2]);
		}
		if(z < 0){
			z = z*-1;
			for(;x>y;x-=z){
				t++;
			}	
		}else{
			for(;x<y;x+=z){
				t++;
			}
		}
		if(x == y){
			System.out.println("steps: " + t);
		}else{
			System.out.println("-1");
		}
	}


}
