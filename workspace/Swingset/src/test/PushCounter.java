package test;

import javax.swing.JFrame;

public class PushCounter {

	public static void main(String[] args) {

		JFrame project = new JFrame("Push Counter");
		project.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PushCounterPanel panel = new PushCounterPanel();
		project.getContentPane().add(panel);
		project.pack();// resize frmae to contain the panels
		project.setVisible(true);
		
	}

}
