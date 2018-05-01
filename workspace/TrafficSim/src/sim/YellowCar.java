package sim;



public class YellowCar extends Warp implements TrafficControl{	
	protected int maxSpeed = TrafficInfo.getYellowSpeedMax();
	private int offset = 5;
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;
	private static int blocks = 0;
	private static int crashes = 0;
	private static int[] turns = new int[2];



	public YellowCar(Direction direction, int i){
		super(direction, i);
		this.setImage("images/topCarYellow.png");

	}
	@Override
	public void blocks() {
		blocks ++;		
	}
	public static int getBlocks() {
		return blocks;
	}
//	@Override
//	protected void choose(Intersection x) {
//		int choose = gen.nextInt(4);
//		if(choose == 2){
//			setRotateR(true);
//			turns[1]++;
//			turning(x);
//		}	
//	}
	@Override
	public void crashes() {
		setCrashes(getCrashes() + 1);		
	}
	public static int getCrashes() {
		return crashes;
	}
	private static void setCrashes(int crashes) {
		YellowCar.crashes = crashes;
	}
	public static int[] getTurns() {
		return turns;
	}



}
