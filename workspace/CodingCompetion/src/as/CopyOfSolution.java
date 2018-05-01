package as;
import java.util.Scanner;


public class CopyOfSolution {

	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("# of Stars");
		int stars = input.nextInt();
		int total = stars;
		stars = 0;	
		int count = 0;
		int rows = 0;
		int rowN = 1;
		int col = 1;
		while(stars != total){
			stars = 0;
			col++;
			for(int i = 1; i<rowN + 1; i ++){
				rows = col;
				if(i%2 == 0){
					rows--;
					stars += rows;
				}else{
					stars += rows;
				}
			}
			System.out.println(stars);
			if(stars== total){
				System.out.println(col + "," + rowN);
				break;
			}
			if(stars>total){
				rowN--;
			}
			stars = 0;
			rowN ++;
			for(int i = 1; i<rowN + 1; i ++){
				rows = col;
				if(i%2 == 0){
					rows--;
					stars += rows;
				}else{
					stars += rows;
				}
			}
			System.out.println(stars);
			if(stars== total){
				System.out.println(col + "," + rowN);
				break;
			}
			if(stars>total){
				rowN--;
				count++;
			}
			
			if(count == 2){
				col--;
				count = 0;
			}
		}
	}
}
