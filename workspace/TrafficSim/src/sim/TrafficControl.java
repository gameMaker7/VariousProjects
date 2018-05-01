package sim;

public interface TrafficControl {


	public void leaving();
	public void approaching(Intersection x);
	public void enter(Intersection x); 
	public void crashes();
	public void blocks();

}
