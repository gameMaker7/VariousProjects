import java.util.Scanner;


public class Calculator {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter two numbers. They will act as x/y,x/y please enter an operation as well. ");
		String convert1 = input.nextLine();
		int plus = convert1.indexOf('+');
		int minus = convert1.indexOf('-');
		int multiply = convert1.indexOf('*');
		int divide = convert1.indexOf('/');

		convert1 = convert1.replaceAll(" ", "");
		String array[] = convert1.split(",");
		String fraction1[] = array[0].split("/");
		String fraction2[] = array[1].split("/");
				
		int fraction1N = Integer.parseInt(fraction1[0]);
		int fraction1D = Integer.parseInt(fraction1[1]);
		float fractionRN = 0;
		float fractionRD = 0;
		float decimal = 0;
		
		int fraction2N = Integer.parseInt(fraction2[0]);
		int fraction2D = Integer.parseInt(fraction2[1]);
		
			
	if(plus>=-1){
		
			fractionRD = fraction1D * fraction2D ;
			fractionRN = (fraction1N * fraction2D) + (fraction2N * fraction1D);
			decimal = fractionRN/fractionRD;
			System.out.println("Your fraction is " + fractionRN + "/" + fractionRD + ".");
			System.out.println("The decimal equivlent is " + decimal + ".");
	
	}else
	
		
	if(minus>= -1){
	
			fractionRD = fraction1D * fraction2D ;
			fractionRN = (fraction1N * fraction2D) - (fraction2N * fraction1D);
			decimal = fractionRN/fractionRD;
			System.out.println("Your fraction is " + fractionRN + "/" + fractionRD + ".");
			System.out.println("The decimal equivlent is " + decimal + ".");
		
	}else
				
	if(multiply>= -1){
		
			fractionRD = fraction1D * fraction2D ;
			fractionRN = fraction1N * fraction2N;
			decimal = fractionRN/fractionRD;
			System.out.println("Your fraction is " + fractionRN + "/" + fractionRD + ".");
			System.out.println("The decimal equivlent is " + decimal + ".");
			
			}else
			
	if(divide>= -1){
	
		fractionRD = fraction1D * fraction2N ;
		fractionRN = fraction1N * fraction2D;
		decimal = fractionRN/fractionRD;
		System.out.println("Your fraction is " + fractionRN + "/" + fractionRD + ".");
		System.out.println("The decimal equivlent is " + decimal + ".");
			}
	
	else{
	System.out.println("you have typed an incorrect statement please type:\n+\n-\n*\nor /");	
	}
	input.close();
	}

}
