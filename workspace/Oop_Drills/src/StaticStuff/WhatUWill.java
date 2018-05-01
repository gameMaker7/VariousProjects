package StaticStuff;

public class WhatUWill {
	int n = method1("Non static"); // 4
	static int m = method1("static int"); // 1
	static {
		System.out.println("static block");//2
		System.out.println("Line 1 of Main");// 3
		new WhatUWill(); 
		System.out.println(method1("within main")); // 6
		X.method2(); // 8
	}
	public WhatUWill(){ // 5
		System.out.println("Constructor");
	}
	public static void main(String[] args) {


	}
	public static int method1(String msg){ 
		System.out.println("Method1: " + msg);
		return 1;
	}
	static{
		int[][] ray = new int[3][];
		ray[0] = new int[1];
		ray[1] = new int[5];
		ray[2] = new int[10];
		for(int i = 0; i<ray.length; i++){
			for(int j = 0; j<ray[i].length; j ++){
				ray[i][j] = 0;
				System.out.print(ray[i][j] + "....");
			}
			System.out.println("");
		}
	}

}
