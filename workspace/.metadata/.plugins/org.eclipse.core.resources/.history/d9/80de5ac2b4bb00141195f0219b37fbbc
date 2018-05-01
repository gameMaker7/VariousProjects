package Swing;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Group.HeroList;
import Group.Party;
import Items.Store;
import Setup.QuestBoard;
import Setup.Save;
import Treasure.Loot;

public class Akatar extends JFrame {
	private static Loot loot = new Loot();
	private static Party party = new Party();
	private static Store shop = new Store();
	private static QuestBoard jobs = new QuestBoard();
	private static int baseLoot = 5;
	private static JFrame frame;
	public static File f;
	private static TavernPanel tavern =  new TavernPanel();
	public static Akatar game;

	public void create(){
		build();
		content();
		finalStuff();
	}
	private static void content() {
		JTabbedPane tab = new JTabbedPane();
		frame.getContentPane().add(tab);
		tab.add("Tavern",tavern);
		tab.add("Shop", new ShopPanel());
		tab.add("Quests", new QuestsPanel());			
	}
	private static void finalStuff() {
		frame.pack();
		frame.setLocation((1920/2) - (frame.getWidth()/2), (1080/2) - (frame.getHeight()/2));
		frame.setVisible(true);			
	}
	private static void build() {
		loot();
		frame = new JFrame("Revoked Godhood");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(300, 400);
		frame.setPreferredSize(d);			
	}
	public static void loot(){
		//			for(Items x: loot.getList()){
		//System.out.println("Lv: " + x.getRank() + " (" + x.getType() + ") " + x.getName());
		//}
		for(int i = 0; i< baseLoot  ; i ++){
			shop.add(loot.getList().get(0));
			loot.getList().remove(0);
			loot.shuffle();
		}
	}
	public static Store getShop() {
		return shop;
	}
	public static Party getParty() {
		return party;
	}
	public static Loot getLoot() {
		return loot;
	}
	public static QuestBoard getJobs() {
		return jobs;
	}
	public static HeroList getAdventure() {
		return party.adventure;
		
	}
	
	public static TavernPanel getTavern() {
		return tavern;
	}
	public void save() {
		try {
			System.out.println(f.getPath());
			FileOutputStream create = new FileOutputStream(f);
			ObjectOutputStream output = new ObjectOutputStream(create);
			output.writeObject(this);
			BasicWindow alert = new BasicWindow("Save Complete");
			output.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
}
