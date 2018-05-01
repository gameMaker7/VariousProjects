//
//
//import greenfoot.Actor;
//
//public class Spawner extends Actor {
//
//	int rotation = 0;
//	int count = Game.gen.nextInt(5) +1 * 25;
//	int spawn = 0;
//	int side = 0;
//	int x = 0;
//	int y = 0;
//	public Spawner(){
//		this.getImage().clear();
//	}
//	public void act(){
//		count--;
//		if(count == 0){
//			gen();
//			spawn = Game.gen.nextInt(100) + 1;
//			if(spawn <= 25)getWorld().addObject(new RedCar(this), x,y);
//			if(spawn > 25 && spawn <= 50)getWorld().addObject(new YellowCar(this),x, y);
//			if(spawn > 50 && spawn <= 75)getWorld().addObject(new PurpleCar(this), x,y);
//			if(spawn > 75)getWorld().addObject(new BlueCar(this), x, y);
//			
//			count = Game.gen.nextInt(300) + 100;
//		}
//	}
//	private void gen(){
//		if(side == 0){
//			x = Game.gen.nextInt(Game.SIZE);
//			y = Game.SIZE-10;
//		}
//		else if(side == 1){
//			x = Game.gen.nextInt(Game.SIZE) - 5;
//			y = Game.gen.nextInt(Game.SIZE);
//		}
//		else if (side == 2){
//			x = Game.gen.nextInt(Game.SIZE);
//			y = 10;
//		}
//		else{
//			x = 5;
//			y = Game.gen.nextInt(Game.SIZE);
//			
//		}
//	}
//}
