package around;

public class Rocks implements Submercible{

	public void submerge(){
		System.out.println("sink.");
	}

	@Override
	public void reveal() {
System.out.println(".....");		
	}
}
