//
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import Interfaces.Crash;
//import greenfoot.Actor;
//
//public class Explosion extends Actor {
//	int ctimer = 0;
//	int maxsize = 100;
//	boolean exp = false;
//
//	public void act(){
//		if(ctimer < maxsize){
//			this.getImage().clear();
//			this.getImage().setColor(Color.RED);
//			this.getImage().scale(ctimer+1,ctimer+1);
//			this.getImage().drawOval(this.getImage().getWidth()/2 - ctimer/2, this.getImage().getHeight()/2 - ctimer/2, ctimer, ctimer);
//
//			ctimer++;
//			ArrayList<Crash> crash = new ArrayList<Crash>();
//			
//		}
//		else
//		{
//			exp = true;
//			
//			this.getWorld().removeObject(this);
//
//		}
//		
//	}
//}
//
