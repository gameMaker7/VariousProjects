package Swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Group.Hclasses;
import Group.Hero;
import Items.Items;
import Setup.Game;

public class HeroInfo extends JFrame implements ActionListener{

	private JButton exit;
	private JComboBox<Items> bag;
	private JPanel infopane;
	private JLabel name;
	private JLabel lv;
	private JLabel gold;
	private JLabel effect;
	private JLabel xp;
	private JButton equip;
	private Hero hero;
	public HeroInfo(Hero hero){
		super("Self Evaluation");
		build(hero);
		content();
		finalStuff();
	}

	private void finalStuff() {
		this.add(infopane);
		this.pack();
		this.setLocation(1920/2 -(this.getWidth()), 1080/2 - (this.getHeight()));
		this.setVisible(true);		
	}

	private void content() {
		exit = new JButton("Exit");
		equip = new JButton("Equip");
		name = new JLabel("Name: " + hero.getName());
		lv = new JLabel("Lv: " + hero.getRank());
		gold = new JLabel("Gold:" + hero.getGold());
		effect = new JLabel("Effective Lv: " + hero.effectiveRank());
		xp = new JLabel("XP: " + hero.getExp() + "/" + hero.getLvUp());
		bag = new JComboBox<Items>();
		for(Items item: hero.getBag().getList()){
			bag.addItem(item);
		}	

		exit.addActionListener(this);
		equip.addActionListener(this);

		infopane.add(exit);
		infopane.add(name);
		infopane.add(lv);
		infopane.add(gold);
		infopane.add(effect);
		infopane.add(xp);
		infopane.add(bag);
		infopane.add(equip);
		
	}

	private void build(Hero hero) {
		this.hero = hero;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = new Dimension(300, 400);
		this.setPreferredSize(d);
		infopane = new JPanel();		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			this.dispose();
		}
		if(e.getSource() == equip){
			if(hero.getBag().length() != 0){
				hero.getBag().unequip();
				EquipMenu menu = new EquipMenu(hero);
			}else{
				BasicWindow alert = new BasicWindow("No items in bag.");		
				this.dispose();
			}
		}
	}
}
