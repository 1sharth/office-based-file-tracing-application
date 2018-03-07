package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import myutilities.Utilities;
import places.Place;
import places.Placeset;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class deleteplaceframe 
{
    Baseframe delplframe = new Baseframe(200,200);
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void starter() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteplaceframe frame = new deleteplaceframe();
					frame.delplframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public deleteplaceframe()
	{
		delplframe.setTitle("PlaceRemover");
		HashMap<String,Place> p = Placeset.getPlaceMap();
		final JComboBox comboBox = new JComboBox(Utilities.mapToStringArray(p));
		comboBox.setBounds(10, 11, 150, 20);
		delplframe.getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name=(String)comboBox.getItemAt(comboBox.getSelectedIndex());
				Placeset.deletePlace(name);
				delplframe.dispose();
				
			}
		});
		btnNewButton.setBounds(10, 42, 62, 23);
		delplframe.getContentPane().add(btnNewButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
	}
}
