package test2;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FlowPanel extends JPanel {

	public FlowPanel(){
		this.setLayout(new FlowLayout());
		this.setBackground(Color.DARK_GRAY);
		JButton[] buttons = new JButton[55];
		for(int i = 0; i<buttons.length; i ++){
			buttons[i] = new JButton("Button " + (i + 1));
			buttons[i].setBackground(Color.red);
			this.add(buttons[i]);
		}
		
	}
}
