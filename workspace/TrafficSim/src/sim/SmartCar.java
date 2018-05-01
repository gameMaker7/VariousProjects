package sim;



public class SmartCar extends Warp implements TrafficControl{	
	protected int maxSpeed = TrafficInfo.getBlueSpeedMax();
	private int offset = 5;
	private int choose = 0;
	private static int blocks = 0;
	private static int crashes = 0;
	private static int[] turns = new int[2];
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;

	public void act(){
		if(start){
			move();
			forward(go);
			try {
				collision();
			} catch (Exception e) {
			}
		}
	}	

	public void startGo(){
		this.go = (Intersection) this.getOneIntersectingObject(Intersection.class);
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public static int getCrashes() {
		return crashes;
	}
	public SmartCar(Direction direction, int i){
		super(direction, i);
		this.setImage("images/topCarBlue.png");

	}

	public void desSearch(Intersection i, Intersection dest) {
		if(i == dest){
			this.start = true;
			return;
		}else{
			this.desDir.add(direction(i, dest));
			i = newDir(i,(direction(i, dest)));
			desSearch(i, dest); 
		}
		//		sqrt(  ((this.getX() - dest.getX())*(this.getX() - dest.getX())) + ((this.getY() - dest.getY())*(this.getY() - dest.getY())) )		
	}

	private Intersection newDir(Intersection i, Direction state) {
		Intersection out = null;
		Intersection[][] check = TrafficWorld.intersection;
			if(state == Direction.EAST){
				out = check[i.getI()][i.getA()-1];	
			}
			if(state == Direction.NORTH){
				out = check[i.getI()+1][i.getA()];	
			}
			if(state == Direction.WEST){
				out = check[i.getI()][i.getA()+1];	

			}
			if(state == Direction.SOUTH){
				out = check[i.getI()-1][i.getA()];	
			}
		return out;
	}
	private Direction direction(Intersection dest, Intersection i) {
		double x = Double.MAX_VALUE;
		Direction state = null;	
		double north = i.north(dest);
		if(north < Double.MAX_VALUE){
		state = Direction.NORTH;
		x = north;
		}
		double south = i.south(dest);
		if(x > south){
			state = Direction.SOUTH;
			x = south;
		}
		double west = i.west(dest);
		if(x > west){
			state = Direction.WEST;
			x = west;
		}
		double east = i.east(dest);
		if(x > east){
			state = Direction.EAST;
		}
		return state;		
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
