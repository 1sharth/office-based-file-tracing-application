package gui;

import users.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Files.Fileset;
import myutilities.Utilities;
import places.Place;
import places.Placeset;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.Window.Type;

public class adddestframe 
{
	private static int n;
	private static String filename;
    Baseframe destframe = new Baseframe(400,80*(n/3),0);
	private JPanel panel;
	private static String uname;

	public static void starter(int m,String fname,String username) 
	{
		n=m+1;
		filename=fname;
		uname=username;
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					adddestframe frame = new adddestframe();
					frame.destframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public adddestframe() 
	{
		
		
		destframe.setTitle("File Stats");
		destframe.setForeground(Color.DARK_GRAY);
		//destframe.setLayout(new GridLayout(0, 1, 0, 0));
		destframe.setBounds(100,100,589,343);
		panel = new JPanel();
		//panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//panel.setLayout(null);
		
		final JComboBox J[]= new JComboBox[n];
		String p[] = Placeset.getPlaceMap();
		int x=0,y=0;
		panel.setLayout(new GridLayout(n+5, 20, 0, 0));
		
		
	    JLabel lblCreationPlace = new JLabel("Creation Place");
		lblCreationPlace.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCreationPlace.setForeground(new Color(0, 0, 0));
		lblCreationPlace.setBounds(19, 11, 97, 14);
		panel.add(lblCreationPlace);
		
		String T[]=new String[1];                                     //giving only one option
		T[0]=MapIdUserPlace.getPLaceName(uname);
		J[0] = new JComboBox(T);
		J[0].setBounds(10, 36, 250, 20);
		panel.add(J[0]);
		
		JLabel lblDestinationList = new JLabel("Destination List");
		lblDestinationList.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblDestinationList.setBounds(19, 80, 108, 14);
		panel.add(lblDestinationList);
		
		
		for(int i=1; i<n; i++)
		{
			
			J[i]= new JComboBox(p);
			J[i].setBounds(10, 100+y, 250, 20);
			y+=40;
			panel.add(J[i]);
			//System.out.println("y="+y);
		}
		//System.out.println("ym for OK button="+(y+100));
		
		JButton but = new JButton("OK");
		but.setFont(new Font("Modern No. 20", Font.PLAIN, 11));
		but.setBounds(10,y+100,55,35);
		panel.add(but);
		
		but.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String destlist="";
				for(int i=0; i<n-1; i++)
				{
					destlist+=(String)J[i].getItemAt(J[i].getSelectedIndex())+',';
				}
				destlist+=(String)J[n-1].getItemAt(J[n-1].getSelectedIndex());
				int id=Fileset.addFile(filename,destlist);
				
				System.out.println("the file id is:"+id);
				UserFileSManager.addFileToUser(uname,id,"C");
				UserFileSManager.mapper(destlist,id);
				
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Your File ID is:");
				label.setBounds(10, 10, 20, 80);
				JTextArea text = new JTextArea(String.valueOf(id));
				text.setBounds(10, 13, 20, 80);
				panel.add(label); panel.add(text);
				JOptionPane.showMessageDialog(destframe,panel);
				
				//JOptionPane.showMessageDialog(destframe,"Your File ID is: "+id,null,JOptionPane.INFORMATION_MESSAGE);
				destframe.dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(304, 122, 2, 2);
		destframe.getContentPane().add(scrollPane);
		
	}
}
