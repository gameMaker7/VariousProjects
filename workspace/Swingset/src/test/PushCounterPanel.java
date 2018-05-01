package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PushCounterPanel extends JPanel{
	private int count = 0;
	private JButton leftButton = new JButton("Push Me");
	private JButton RightButton = new JButton("Push Me");
	private JLabel label = new JLabel("Pushes: " + count);
	private JCheckBox bold = new JCheckBox("Bold?");
	private JCheckBox itallic = new JCheckBox("Itallic?");
	private int style = Font.PLAIN;
	private JTextField typeSpace = new JTextField("", 5);// can also be set to textfield(5) 
	public PushCounterPanel(){

		ButtonListener blarg = new ButtonListener();
		FontListener bort = new FontListener();

		leftButton.addActionListener(blarg);
		RightButton.addActionListener(blarg);
		typeSpace.addActionListener(blarg);
		bold.addItemListener(bort);
		itallic.addItemListener(bort);

		this.add(leftButton);
		this.add(RightButton);
		this.add(label);
		this.add(typeSpace);
		this.add(bold);
		this.add(itallic);


		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(300, 400));
	}
	private class FontListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(bold.isSelected()){
				style = Font.BOLD;
			}
			if(itallic.isSelected()){
				style = Font.ITALIC;
			}

		}

	}
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == leftButton){
				label.setText("duh");

			}
			else if(e.getSource() == RightButton){
				label.setText("Idiot");
			}
			else if(e.getSource() == typeSpace){
				label.setText(typeSpace.getText());
				typeSpace.setText("");
			}

			label.setFont(new Font("Helvetica", style, 20));
		}
	}
}

