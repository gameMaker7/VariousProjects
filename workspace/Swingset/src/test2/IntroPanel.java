package test2;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {

	JLabel label = new JLabel("Choose a Tab.");
	
	public IntroPanel(){
		this.setBackground(Color.GRAY);
		this.add(label);
	}
}
