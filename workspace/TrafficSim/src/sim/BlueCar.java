package sim;



public class BlueCar extends NonWarp implements TrafficControl{	
	protected int maxSpeed = TrafficInfo.getBlueSpeedMax();
	private int offset = 5;
	private int choose = 0;
	private static int blocks = 0;
	private static int crashes = 0;
	private static int[] turns = new int[2];
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;


	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public static int getCrashes() {
		return crashes;
	}
	public BlueCar(Direction direction, int i){
		super(direction, i);
		this.setImage("images/topCarBlue.png");

	}
//	@Override
//	protected void choose(Intersection x) {
//		if(choose == 0){
//			choose ++;
//			setRotateR(true);
//			turns[1]++;
//			turning(x);
//		}
//		if(choose == 1){
//			choose --;
//			setRotateL(true);
//			turns[0]++;
//			turning(x);
//		}		
//	}
	public static int[] getTurns() {
		return turns;
	}
	@Override
	public void crashes() {
		crashes++;		
	}
	@Override
	public void blocks() {
		blocks++;		
	}
	public static int getBlocks() {
		return blocks;
	}

}