package sim;



public class RedCar extends Warp implements TrafficControl{	
	protected int maxSpeed = TrafficInfo.getRedSpeedMax();
	private int offset = 5;
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;
	private int count = 20;
	private static int blocks = 0;
	private static int crashes = 0;
	private static int[] turns = new int[2];



	public RedCar(Direction direction, int i){
		super(direction, i);
		this.setImage("images/topCarRed.png");

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
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void crashes() {
		setCrashes(getCrashes() + 1);		
	}

	public static int getCrashes() {
		return crashes;
	}

	private static void setCrashes(int crashes) {
		RedCar.crashes = crashes;
	}

	public static int[] getTurns() {
		return turns;
	}

}	