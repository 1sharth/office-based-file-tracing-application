package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class asdf extends JFrame
{



	
	public static void main(String[] args)
	{
		new asdf();
	}

	/**
	 * Create the frame.
	 */
	public asdf()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 256, 147);
 

		
		
		
		JLabel label = new JLabel("New label");
		label.setBounds(10, 0, 200, 50);
		add(label);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 57, 200, 40);
		add(lblNewLabel);
		
		setVisible(true);
	}
}
