package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import myutilities.Utilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class addfileframe 
{
    Baseframe addfileframe = new Baseframe(303,147);
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private static String uname;
	/**
	 * Launch the application.
	 */
	public static void starter(String name) 
	{
		uname=name;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addfileframe frame = new addfileframe();
					frame.addfileframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addfileframe()
	{
		
		
		addfileframe.setTitle("Addfile");
		
		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblFilename.setBounds(10, 11, 51, 33);
		addfileframe.getContentPane().add(lblFilename);
		
		textField = new JTextField();
		textField.setBounds(71, 17, 169, 20);
		addfileframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNoOfDestinations = new JLabel("No of Destinations");
		lblNoOfDestinations.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNoOfDestinations.setBounds(10, 48, 116, 50);
		addfileframe.getContentPane().add(lblNoOfDestinations);
		
		textField_1 = new JTextField();
		textField_1.setBounds(116, 63, 51, 20);
		addfileframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String filename = textField.getText();
				String dest=textField_1.getText();
				
				if(!Utilities.strValidation(filename))
				{
					JOptionPane.showMessageDialog(addfileframe,"Improper Name: only valid characters and spaces allowed","Try Again",JOptionPane.WARNING_MESSAGE);
				}
				else if(!Utilities.strNumValidation(dest))
				{
					JOptionPane.showMessageDialog(addfileframe,"Improper Value: only integer allowed","Try Again",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
				  int no_dest = Integer.parseInt(textField_1.getText());	
				  addfileframe.setEnabled(false);
				  adddestframe.starter(no_dest,filename,uname);
				  addfileframe.setEnabled(true);
				  addfileframe.dispose();
				}
			}
		});
		btnNewButton.setBounds(186, 62, 65, 23);
		addfileframe.getContentPane().add(btnNewButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
	}

}
