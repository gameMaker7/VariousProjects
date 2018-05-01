package sim;



public class PurpleCar extends NonWarp implements TrafficControl{	
	protected int maxSpeed = TrafficInfo.getPurpleSpeedMax();
	private int offset = 5;
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;
	private static int blocks = 0;
	private static int crashes = 0;
	private static int[] turns = new int[2];



	public PurpleCar(Direction direction, int i){
		super(direction, i);
		this.setImage("images/topCarPurple.png");
	}
	@Override
	public void blocks() {
		blocks ++;		
	}
	public static int getBlocks() {
		return blocks;
	}
//	@Override
//	public void choose(Intersection x) {
//		int choose = gen.nextInt(4);
//		if(choose == 1){
//			setRotateL(true);
//			turns[0]++;
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
		PurpleCar.crashes = crashes;
	}
	public static int[] getTurns() {
		return turns;
	}
}
