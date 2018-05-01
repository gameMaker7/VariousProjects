package Dialog;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Dialog {


	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame();
			//			String a = JOptionPane.showInputDialog("Enter a Number");
			//			int i = Integer.parseInt(a);
			//			JOptionPane.showMessageDialog(null, "The Number is: " + i);

			JFileChooser jfile = new JFileChooser();
			int i = jfile.showDialog(frame, null);
			if(i == JFileChooser.APPROVE_OPTION){
				File file = jfile.getSelectedFile();
				Scanner scan = new Scanner(file);
				
				StringBuilder build = new StringBuilder();
				while(scan.hasNextLine()){
					build.append(scan.nextLine());
					build.append("/n");
				}
				JTextArea box = new JTextArea(20,40);
				box.setText(build.toString());z
				frame.add(box);
			}
			
			frame.pack();
			frame.setVisible(true);

	}

}
