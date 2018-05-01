package sim;

public class TrafficInfo {

	private static int redSpeedMax;
	private static int BlueSpeedMax;
	private static int YellowSpeedMax;
	private static int PurpleSpeedMax;
	
	private static int ROADS;
	private static int counter;

	public TrafficInfo(int a, int b, int c, int d, int e, int g){
		redSpeedMax = a;
		BlueSpeedMax = b;
		YellowSpeedMax = c;
		PurpleSpeedMax = d;
		
		ROADS = e; 
		counter = g;	
	}
	public static int getRedSpeedMax() {
		return redSpeedMax;
	}
	public static int getBlueSpeedMax() {
		return BlueSpeedMax;
	}
	public static int getYellowSpeedMax() {
		return YellowSpeedMax;
	}
	public static int getPurpleSpeedMax() {
		return PurpleSpeedMax;
	}
	public static int getROADS() {
		return ROADS;
	}
	public static int getCounter() {
		return counter;
	}

	
}
