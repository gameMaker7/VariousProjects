import java.util.Random;
import java.util.Scanner;


public class Equations {
	final int MAX = 10;
	int x = 0;
	int negative = 0;
	int m = 0;
	int c = 0;
	int y = 0;
	int a = 0;
	int b = 0;
	float x2 = 0;
	float v = 0;
	boolean good = false;
	int degree = 2;
	static int correct = 0;
	static int total = 0;
	static int wrong = 0;
	Scanner input = new Scanner(System.in);
	public void setup(){
		Random gen = new Random();
		do{
			m = gen.nextInt(MAX) + 1;
			negative = gen.nextInt(MAX);
			if(m < negative){
				m *= -1;
			}
			b = gen.nextInt(MAX);
			negative = gen.nextInt(MAX);
			if(b < negative){
				b *= -1;
			}
			c =  gen.nextInt(MAX);
			negative = gen.nextInt(MAX);
			if(c < negative){
				c *= -1;
			}
			x =  gen.nextInt(MAX);
			negative = gen.nextInt(MAX);
			if(x < negative){
				x *= -1;
			}
			y = (((x * x) * m) + (b * x) + c);
			x2 = -y + c;			
			v = x2 / x;
			v = v / m;
			
		}while(x != (int)x || y > 80 || y < -80 || v != (int)v);

	}
	public static void main(String[] args) {
		while(wrong < 5){
			Equations play = new Equations();
			play.setup();
			play.play();
		}
		System.out.println("accuracy " + (correct/total) * 100);
	}
	public void play(){
		if(b != 0){
		System.out.println("If y = " + y + " \nsolve for x: " + m + "x" + "^" + degree + " + " + b + "x + (" + c + ")");
		}
		else{
			System.out.println("If y = " + y + " \nsolve for x: " + m + "x" + "^" + degree + " + (" + c + ")");
			
		}
		a = input.nextInt();
		if(a == (int)x || a == v){
			System.out.println("correct");
			correct ++;
			total ++;
		}
		else{
			System.out.println("Correct Answer is " + (int)x + " and " + (int)v);
			wrong ++;
			total ++;
		}
	}

}
