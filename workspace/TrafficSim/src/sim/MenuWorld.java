package sim;

import greenfoot.Greenfoot;
import greenfoot.World;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuWorld extends World{

	public MenuWorld(){
		super(1000,750,1);
		new Controls();
	}

	class Controls extends JFrame implements ActionListener{

		private JButton start;
		private JTextField hroads;
		private JTextField vroads;
		private JTextField timer;

		public Controls(){
			super("TrafficSim Menu");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension d = new Dimension(300,400);
			this.setPreferredSize(d);
			
			JPanel cpanel = new JPanel();
			
			start = new JButton("Start");
			start.addActionListener(this);
			cpanel.add(start);

			hroads = new JTextField("Horizontal Roads", 5);
			cpanel.add(hroads);
			vroads = new JTextField("Vertical Roads", 5);
			cpanel.add(vroads);
			timer = new JTextField("Timer", 5);
			cpanel.add(timer);
			
			this.add(cpanel);
			
			this.pack();
			this.setLocation((1920/2)-(this.getWidth()/2), (1080/2)-(this.getHeight()/2));
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == start){
//				TrafficWorld.build(6, 8, 3000);
				Greenfoot.start();
				this.dispose();
			}
		}
	}
}
