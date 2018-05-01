package test2;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class LayoutDemo {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Layout Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(300, 400);
		frame.setPreferredSize(d);
		
		JTabbedPane tab = new JTabbedPane();
		frame.getContentPane().add(tab);
		tab.add("Intro", new IntroPanel());
		tab.add("Flow", new FlowPanel());
		tab.add("Border", new BorderLayoutPanel());
		
		frame.setVisible(true);
		frame.pack();
	}
}
