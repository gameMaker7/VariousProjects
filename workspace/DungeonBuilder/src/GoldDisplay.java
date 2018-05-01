import java.awt.Color;

import greenfoot.Actor;


public class GoldDisplay extends Actor{
	int gold = 50;
	boolean drawn = false;

	public GoldDisplay(){
		this.setImage("images/Gold Display.png");
	}

	public void act(){
		if(!drawn){
			this.getImage().clear();
			this.setImage("images/Gold Display.png");
			this.getImage().setColor(Color.YELLOW);
			String money = Integer.toString(gold);
			this.getImage().drawString(money, 80, 20);
			drawn = true;
		}
	}

	public void addMoney(int gained){
		this.gold += gained;
		drawn = false;
	}
	
	public void spendMoney(int spend){
		this.gold -= spend;
		drawn = false;
	}
	
	public boolean canSpend(int spend){
		if(this.gold - spend >= 0){
			return true;
		}else{
			return false;
		}
	}
}