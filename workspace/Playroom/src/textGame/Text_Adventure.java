package textGame;
import java.util.Random;
import java.util.Scanner;


public class Text_Adventure {

	//move
	//interact with objects
	//talk 
	//unlock dooor

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random gen = new Random();	

		boolean sword = false;  // does + 3 damage all weapons
		boolean staff = false;  
		boolean axe = false; 

		boolean entranceChamber = false;
		boolean entranceSearched = false;

		boolean armory = false;
		boolean armorySearched = false;

		boolean darkChamber = false;

		boolean hiddenRoom = false;

		boolean secretRoom = false; 

		boolean throneRoom = false;
		boolean throneRoomSearched = false;

		boolean key = false;   // required
		boolean rope = false;  // required
		boolean lantern = false; // useful for dark chamber
		boolean ring = false;  // opens secret chamber
		boolean armor = false;  // increase health by 5
		boolean seal = false; // required
		boolean crown = false;  // finish coondition
		boolean orb = false; // restores full life or kills one creature
		boolean dias = false;
		boolean lever = false;
		int diasPieces = 0;
		boolean vault=false;


		int health = 10;
		int damage = gen.nextInt(10) + 1;
		int spider1Life = gen.nextInt(8) + 3;
		int spider2Life = gen.nextInt(8) + 3;


		int chest = gen.nextInt(3);
		String begin ="";
		while(!begin.equals("yes")){
			System.out.println("Common actions include search, check, drop, move\nyes or no are used often as well\nin other cases the correct term will be in the text have fun.");
			System.out.println("");	
			System.out.println("You are a canidate for the kingdom of Agrador.\nThe true king is the only person who can retrieve the crown from the anchient castle.");
			System.out.println("You must find the crown and exit the castle.\nEnter he castle with nothing and only bring the crown out.\nUnderstood?");
			begin = input.next();
		}
		String action = "";
		while(health>0){
			if(begin.equalsIgnoreCase("yes")){

				System.out.println("The castle is a decripit structure and could easily fall apart.\nAs I enter the chamber I notice a hole in the wall and various other items.");
				entranceChamber = true;		// begining text
				while(!action.equals("stop")){
					while(entranceChamber && health > 0){
						System.out.println("What should I do.");	
						action = input.next();

						switch(action){	//interact with entrancecamber
						case "search":
							entranceSearched = true;
							System.out.println("A cabinet and closet can be seen in the room.");
							break;

						case "check": 
							if(!entranceSearched){ System.out.println("Have not searched room yet.");}
							if(entranceSearched){
								System.out.println("check cabinet or closet");
								action = input.next();
								switch(action){	// inside check
								case "cabinet":
									if(!rope){
										System.out.println("There is a rope in here. Take it?");
										action = input.next();
										switch(action){		//inside cabinet		
										case "yes":
											rope = true;
											break;
										}
									}
									else{System.out.println("Nothing here.");
									}
									break;

								case "closet":
									if(!lantern){
										System.out.println("There is a lantern in here. Take it?");
										action = input.next();

										switch(action){
										case "yes":
											lantern = true;
											break;
										}
									}
									else{System.out.println("Nothing here.");
									}				
									break;
								}
							}

						case "inventory":
							if(rope){System.out.println("rope");
							}
							if(lantern){System.out.println("lanturn");
							}
							break;

						case "move":
							System.out.println("Go through hole in wall?");
							action = input.next();
							switch(action){
							case "yes":
								armory = true;
								entranceChamber = false;
								break;
							}
						}
					}


					if(armory && health>0){
						System.out.println("I enter a large room which looks like an armory.");
					}

					while(armory && health > 0){
						System.out.println("What should I do?");
						action = input.next();

						switch(action){
						case "search":
							System.out.println("The armory has lots of chests and racks.");
							armorySearched = true;			
							break;
						case "check":
							if(!armorySearched){ System.out.println("Have not searched room yet.");
							}

							if(armorySearched){
								System.out.println("There are racks and chests, which should I check?");
								action = input.next();

								switch(action){	// inside check
								case "racks":
									System.out.println("there are two racks, check left or right?");
									action = input.next();

									switch(action){
									case "left":
										if(staff){
											System.out.println("There's nothing here.");
										}

										if(!staff && !axe && !sword){System.out.println("An iron staff is hidden in the back. Should I take it?");
										action = input.next();

										switch(action){
										case "yes":
											staff = true;
											damage-=1;
											health+=5;
											break;
										}
										}
										else{
											System.out.println("I allready have a weapon I should drop that one before picking up the staff");}
										break;

									case "right":
										if(axe && key){
											System.out.println("Nothing here.");
										}
										if(!key && axe){
											System.out.println("There's a key in here not sure how I missed it.\nShould I take it?");
											action = input.next();
											switch(action){
											case "yes":
												key = true;
												break;
											}
											if(!key && (sword || staff)){
												System.out.println("There is a small key in here. Shouls I take it?");
											}
											action = input.next();
											switch(action){
											case "yes":
												key = true;
											}
										}

										if(!axe && !staff && !sword){
											System.out.println("There's an old axe in the bottom compartment.\nShould I take it.");
											action = input.next();

											switch(action){
											case "yes":
												axe = true;
												health-=1;
												damage+=2;
												break;
											}
										}else{
											System.out.println("I allready have a weapon I should drop that one before picking up the axe");
										}
									}
									break;
								case "chests":
									System.out.println("There are three chests which should I choose?");
									action = input.next();

									switch(action){
									case "left":
										if(chest==0){
											System.out.println("Arrow trap. You Died.");
										health = 0;
										continue;
										}
										if(chest==1){
											if(!armor){

												System.out.println("Armor found. Should I take it?");
												action = input.next();

												switch(action){
												case "yes":
													armor = true;
													health+=5;
													break;
												}
												break;
											}

											else{
												System.out.println("nothing here.");
												break;
											}
										}
										if(chest==2 && !sword && !staff && !axe){
											System.out.println("I found a sword. Should I take it?");
											action = input.next();

											switch(action){
											case "yes":
												sword = true;
												damage++;
												break;
											}
											if(!sword && (axe || staff)){
												System.out.println("I allready have a weapon I should drop that one before picking up the sword");}
											break;
										}
										if(sword){System.out.println("nothing here.");
										break;
										}
									}

								case "middle":
									if(chest==2){
										System.out.println("Fireball trap. You died");
										health = 0;
										break;
									}
									if(chest==0 && !armor){
										System.out.println("Armor found. Should I take it?");
										action = input.next();

										switch(action){
										case "yes":
											armor = true;
											health+=5;
											break;
										}
										break;
									}
									if(armor){
										System.out.println("nothing here.");
									}
									if(chest==1 && !sword && !staff && !axe){
										System.out.println("I found a sword. Should I take it?");
										action = input.next();

										switch(action){
										case "yes":
											sword = true;
											damage++;
											break;}
										if(!sword && (axe || staff)){
											System.out.println("I allready have a weapon I should drop that one before picking up the sword");}
										break;
									}
									if(sword){
										System.out.println("nothing here.");
										break;
									}

								case "right":
									if(chest==1){
										System.out.println("Nothing here.");
									}
									if(chest==2 && !armor){
										System.out.println("Armor found. Should I take it?");
										action = input.next();

										switch(action){
										case "yes":
											armor = true;
											health+=5;
											break;
										}
										break;
									}
									if(armor){
										System.out.println("nothing here.");
										break;
									}
									if(chest==0 && !sword && !staff && !axe){
										System.out.println("I found a sword. Should I take it?");
										action = input.next();

										switch(action){
										case "yes":
											sword = true;
											damage++;
											break;
										}
										if(sword){
											System.out.println("nothing here.");
											break;
										}
										if(!sword && (axe || staff)){
											System.out.println("I allready have a weapon I should drop that one before picking up the sword");
										}
										break;
									}
								}
							}


						case "inventory":
							if(rope){System.out.println("rope");
							}
							if(lantern){System.out.println("lantern");
							}
							if(sword){System.out.println("sword");
							}
							if(axe){System.out.println("axe");
							}
							if(armor){System.out.println("armor");
							}
							if(staff){System.out.println("staff");
							}
							if(key){System.out.println("key");
							}
							break;

						case "drop":
							System.out.println("What item do you want to drop?");
							action = input.next();

							switch(action){
							case"rope ":System.out.println("rope dropped");
							rope = false;
							break;
							case"lantern":System.out.println("lantern dropped.");
							lantern = false;
							break;
							case "sword": System.out.println("sword dropped");
							sword = false;
							break;
							case"axe":System.out.println("axe dropped");
							axe = false;
							break;
							case"armor":System.out.println("armor drooped.");
							armor = false;
							break;
							case"staff": System.out.println("staff dropped.");
							staff = false;
							break;
							case "key": System.out.println("key");
							key = false;
							break;
							}

						case "move":
							System.out.println("Return to entrance or go up the stairs?");
							action = input.next();

							switch(action){
							case "entrance":
							case "return":
								armory = false;
								entranceChamber= true;
								break;
							case "stairs":
								armory=false;
								entranceChamber = false;
								darkChamber=true;

								System.out.println("It's pitch black in here.");
								if(lantern){
									System.out.println("I think I should use the lantern");
									System.out.println("After turning the lantern on I can see a long hallway that is very dark");
								}
								break;
							}
						}
					}

					while(darkChamber && health > 0){
						System.out.println("What should I do");
						action = input.next();

						switch(action){
						case "search":
							if(!lantern){
								System.out.println("The dark chamber gives no warning as a pair of large spiders decend on you.");
								health=0;
								continue;
							} else {
								System.out.println("As I'm searching the room I come across two large spiders. I could charge them, avoid them or maybe distract one.");

								while(spider1Life>0 && spider2Life>0){
									System.out.println("What should I do");
									action = input.next();

									switch(action){
									case "charge":
										System.out.println("This was a terrible idea. As you charge the spiders one notices you and\n smacks you with his leg smashing you into a weapon rack. ");
										health-=3;
										System.out.println("A spear falls next to you.");
										int Spear=gen.nextInt(2);
										if(Spear==0){
											System.out.println("However while trying to grap the spear you are bitten by the spider.");
											health=0;
											continue;									}
										if(Spear == 1){
											System.out.println("You catch the spear and trow it at one of the spiders");
											spider2Life=0;
										}
									case "distract": 
										System.out.println("One of the spiders comes your way. Be ready to attack.");

										while(spider1Life>0 && health>0){

											System.out.println("you can use quick attack, strong attack or dodge.");
											System.out.println("Your current health is "+ health + " .");
											action = input.next();

											switch(action){
											case "quick":
												System.out.println("You attack with your weapon dealing some damage but staying on defensive");
												spider1Life = spider1Life - gen.nextInt(damage)-2;
												health -= gen.nextInt(3);
												break;
											case "strong":
												System.out.println("Powerful atttacks to the spider damage it severly but you are tired now and must rest a second");
												spider1Life-=gen.nextInt(damage) + 2;
												health-=gen.nextInt(5);
												break;
											case "dodge":
												System.out.println("dodging the spider attacks is easy and you start to see patterns in how they move.");
												damage++;break;
											}
										}

										if(spider1Life<=0 && !ring){
											System.out.println("this spider is dead and it had a ring on its leg. should I pick it up?");
											action = input.next();

											switch(action){
											case "yes":
												ring = true;
												break;
											}
											if(ring && spider2Life>0){
												System.out.println("That other spider is far away I can leave now and it won't prusue me.");
												darkChamber = false;
												throneRoom = true;
												break;
											}
											if(spider2Life==0){
												System.out.println("Tje other spider is dead I should leave before more sow up");
												darkChamber = false;
												throneRoom = true;
												break;
											}
										}

									case "avoid": 
										System.out.println("I avoid the spiders and run into the next room.");
										darkChamber = false;
										throneRoom = true;
										break;
									}
								}
							}

						case "inventory":

							if(rope){System.out.println("rope");
							}
							if(lantern){System.out.println("lantern");
							}
							if(sword){System.out.println("sword");
							}
							if(axe){System.out.println("axe");
							}
							if(armor){System.out.println("armor");
							}
							if(staff){System.out.println("staff");
							}
							if(key){System.out.println("key");
							}
							if(ring){System.out.println("ring");
							}

						case "drop":

							System.out.println("do you want to drop an item?");
							action = input.next();

							switch(action){
							case"rope ":System.out.println("rope dropped");
							rope = false;
							break;
							case"lantern":System.out.println("lantern dropped.");
							lantern = false;
							break;
							case "sword": System.out.println("sword dropped");
							sword = false;
							break;
							case"axe":System.out.println("axe dropped");
							axe = false;
							break;
							case"armor":System.out.println("armor drooped.");
							armor = false;
							break;
							case"staff": System.out.println("staff dropped.");
							staff = false;
							break;
							case "key": System.out.println("key");
							key = false;
							break;
							case "ring": System.out.println("Ring Dropped.");
							ring = false;
							break;}

						case "move":
							System.out.println("Enter the throne room?");
							action=input.next();

							switch(action){
							case "yes":
								darkChamber=false;
								throneRoom=true;
							}
							break;
						}
					}

					if(throneRoom && !crown){
						System.out.println("he throne room has a chamber which stores the castle's treasures knwn as the vualt.");
						System.out.println("The vualt is behind the throne chair of the king");
						System.out.println("As you mve toward the vault door you realize this is going to be a challenge the door is locked but the locking mechanism is not visible");

						while(throneRoom && !crown){
							System.out.println("what will you do.");
							action=input.next();

							switch(action){
							case "search":
								throneRoomSearched=true;
								System.out.println("You notice a lever high up on the wall by the door.\nMaybe a rope could be used to pull it down");
								System.out.println("you also notice a embelm on the throne chair.");
								break;
							case "check": 
								if(!throneRoomSearched){
									System.out.println("you unknowingly trip a magic seal destroying the entire castle.\nThe kingdom is dooomed.");
									health=0;
									continue;
								}
								if(throneRoomSearched && diasPieces==0 ){
									System.out.println("You have done this enough that finding the orb inside of a bookcase\nand the seal in the chest were both easy");
									System.out.println("Which leaves the throne and the door to figure out.");
									orb=true;
									seal=true;
								}
								if(diasPieces>0){
									System.out.println("throne, dias or door");}
								action=input.next();

								switch(action){
								case "throne": 
									System.out.println("The throne is made of wood and silk thee embelm on the throne armrest is identical to that of the the ring worn by the king.");
									System.out.println("maybe that ring could be used to reveal something important");
									if(ring){
										System.out.println("The ring you got from the spider fits perfectly");
										dias=true;ring=false;
										if(!ring && !dias){
											System.out.println("While searching for the ring you hear load noises from outside.");
											System.out.println("one of your kingdom's enemies has attacked the castle");
											health=0;
											System.out.println("The castle falls and you perish inside it.");
											health=0;
											continue;
										}
									}
								case"dias":
									if(dias){
										System.out.println("You look at the strange object it seems to be split into three parts.\nA key slot, A pedestal with a bowl like center, and a disk chaped hole.");
										if(key){System.out.println("The key you fund in the armory fits.");
										}
										key=false; diasPieces++;
										if(seal){
											System.out.println("The seal is placed and lights up.");
											seal=false;diasPieces++;
										}
										if(orb){
											System.out.println("The orb rests easily on the pedestal.");
											orb=false;diasPieces++;
										}
										if(diasPieces==3){
											System.out.println("The dias begins to shine a bright light which hits the vault door.");
											if(armor){
												System.out.println("however the light reacts strangley to your armor");
												health=0;
												System.out.println("Welcome to heaven.");
												continue;
											}
											break;
										}
									}
								case"door": 
									System.out.println("The vualt door is large and made entirely of metal.");
									if(rope){
										System.out.println("The rope you got in the entrance is long enough to use for pulling the lever down");
										rope=false;
										lever=true;
									}
									if(lever){
										System.out.println("The lever caused a piece of the door to move revealing a piece of glass");}
									if(diasPieces==3){
										System.out.println("The dias light shines through the glass and causes some strange sounds before long the door opens though.");
										vault=true;
										break;
									}
									if(!rope){
										System.out.println("You jump up to the lever and activate a spell rune cuasing a massive temperature drop.");
										System.out.println("you freeze to death in seconds.");
										health=0;
										continue;
									}
								break;
								}

							case "drop":
								System.out.println("What item do you want to drop?");
								action = input.next();

								switch(action){
								case"rope ":System.out.println("rope dropped");
								rope = false;
								break;
								case"lantern":System.out.println("lantern dropped.");
								lantern = false;
								break;
								case "sword": System.out.println("sword dropped");
								sword = false;
								break;
								case"axe":System.out.println("axe dropped");
								axe = false;
								break;
								case"armor":System.out.println("armor drooped.");
								armor = false;
								break;
								case"staff": System.out.println("staff dropped.");
								staff = false;
								break;
								case "key": System.out.println("key");
								key = false;
								break;}

							case "move": 
								if(vault){
									System.out.println("The vault dor is open and the crown can be easily seen shuld I go in?");
									action=input.next();
									if(action.equals("yes") && !lantern && !sword && !axe && !staff && diasPieces==3){
										System.out.println("Congrats you are now king.");
										crown=true;
										continue;
									}
									if(!vault){
										System.out.println("In your attempts to break open the vault door the casle was destroyed.");
										health=0;
										continue;
									}
									if(action.equals("yes")&& (lantern || sword || axe || staff)){
										System.out.println("You exit the castle with the crown in tow but you are disqualified.");
										System.out.println("You were nstructed to bring only the crown out of the castle. Your claim for kingship ends with your headless corpse.");
										health=0;
										continue;
									}
								}
							}
						}
					}

					if(crown){
						System.out.println("Game over. You win.");
						action= "stop";
					}		

					if(health<=0){
						action ="stop";
					}
				}
			}		
		}
		
		if(health<=0){
			System.out.println("Game over. You have 0 health");
			input.close();
		}
	}
}

