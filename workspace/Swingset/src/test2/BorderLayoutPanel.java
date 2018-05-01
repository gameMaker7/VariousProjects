package test2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BorderLayoutPanel extends JPanel {

	public BorderLayoutPanel(){
		this.setLayout(new BorderLayout());
		this.setBackground(Color.DARK_GRAY);
		JButton[] buttons = new JButton[5];
		String[] location = {BorderLayout.CENTER, BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.WEST, BorderLayout.SOUTH};
		for(int i = 0; i<buttons.length; i ++){
			buttons[i] = new JButton("Button " + (i + 1));
			this.add(buttons[i], location[i]);
		}
	}
}
