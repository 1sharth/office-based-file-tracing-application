package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import myutilities.Utilities;
import places.Placeset;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class placeaddframe
{
	
    Framenotitle placeframe = new Framenotitle(317,154);
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private static Object pframe;

	public static void starter(Object frame) 
	{
		Framenotitle.setDefaultLookAndFeelDecorated(true);
		pframe=frame;
		new placeaddframe();
	    
	}

	public placeaddframe() 
	{
		Utilities.removeMinMaxClose(placeframe);
		((Baseframe)pframe).setEnabled(false);
		
		placeframe.setTitle("NewPlace");
		
		JLabel lblPlacename = new JLabel("PlaceName:");
		lblPlacename.setBounds(16, 11, 71, 30);
		placeframe.getContentPane().add(lblPlacename);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(16, 42, 71, 30);
		placeframe.getContentPane().add(lblLocation);
		
		
		
		textField = new JTextField();              //place
		textField.setBounds(85, 16, 178, 20);
		placeframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();           //location
		textField_1.setBounds(85, 47, 178, 20);
		placeframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnOk = new JButton("ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String placestr=textField.getText();
				String locstr=textField_1.getText();
				
				if(!Utilities.strValidation(placestr) || !Utilities.strValidation(locstr))
					JOptionPane.showMessageDialog(placeframe,"Improper Name","Try Again",JOptionPane.WARNING_MESSAGE);
				else
				{
				  Placeset.addPlace(placestr,locstr);
				  placeframe.dispose();
				  ((Baseframe) pframe).setEnabled(true);
				  Framenotitle.setDefaultLookAndFeelDecorated(false);
				}
				
			}
		});
		btnOk.setBounds(85, 78, 49, 31);
		placeframe.getContentPane().add(btnOk);
		
		JButton btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				placeframe.dispose();
				((Baseframe) pframe).setEnabled(true);
				Framenotitle.setDefaultLookAndFeelDecorated(false);
			}
		});
		btnNewButton.setBounds(148, 78, 55, 31);
		placeframe.getContentPane().add(btnNewButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
	}
}
