package gui; 

import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Files.*;
import places.*;
import users.UserFileSManager;
import users.MapIdUserPlace;
import users.UserSet;
import Alphanumeric.*;
import myutilities.*;

import java.awt.Color;
import java.awt.Font;

import global.Global;


class Framenotitle extends JFrame
{
	Framenotitle(int x,int y)
	{
		
		setLayout(null);
		setBounds(100, 100, x, y);
		setVisible(true);//making the baseframe visible
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		//setUndecorated(true);
	}
}

class Baseframe extends JFrame
{
	//JFrame baseframe = new JFrame();
	Baseframe(int x,int y)
	{ 
		setBounds(100, 100, x, y);
		//getContentPane().setLayout(null);
		setLayout(null);//using no layout managers  
		setVisible(true);//making the baseframe visible
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	Baseframe(int x,int y,int z)
	{ 
		setBounds(100, 100, x, y);
		setVisible(true);//making the baseframe visible
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}

public class Starter
{
	Baseframe baseframe = new Baseframe(274,225);
	
	public Starter() 
	{
		try 
		{
		  Global.conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/filemanagement","root","");
		  if(Global.conn!=null)
			System.out.println("connection established");
	    }
		catch (SQLException e) {e.printStackTrace();}
		
		initialize();
	}
    
	public void Enabled(boolean x)
	{
		if(x==true)
			baseframe.setEnabled(true);
		else
			baseframe.setEnabled(false);
	}
	/**
	 * Initialize the contents of the baseframe.
	 */
	private void initialize() 
	{
		
		
		JButton b_addplace = new JButton("Add Place");
		b_addplace.setForeground(Color.WHITE);
		b_addplace.setDefaultCapable(false);
		b_addplace.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		b_addplace.setBackground(Color.BLACK);
		b_addplace.setFont(new Font("Tahoma", Font.PLAIN, 12));
		b_addplace.setBounds(10, 11, 106, 23);
		baseframe.getContentPane().add(b_addplace);
		b_addplace.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				placeaddframe.starter(baseframe);
				//baseframe.setEnabled(true);
				//System.out.println(name);
			}
		});
		
		
		
		
		
		
		JButton b_deletepl = new JButton("Delete Place");
		b_deletepl.setForeground(Color.WHITE);
		b_deletepl.setBackground(Color.BLACK);
		b_deletepl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		b_deletepl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				baseframe.setEnabled(false);
				deleteplaceframe.starter();
				baseframe.setEnabled(true);
			}
			
		});
		b_deletepl.setBounds(10, 45, 106, 23);
		baseframe.getContentPane().add(b_deletepl);
		
		
		
		
		JButton btnNewButton_1 = new JButton("Show AllFiles");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Map m = Fileset.getMap();
				String Arr[]=Utilities.mapToStringArray_v(m); //all map values turn into Strings array
				String list="";
				for(int i=0; i<Arr.length; i++)
				{
					list+=Arr[i]+'\n';
				}
				JOptionPane.showMessageDialog(baseframe,list,"All files",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnNewButton_1.setBounds(126, 45, 106, 23);
		baseframe.getContentPane().add(btnNewButton_1);
		baseframe.getContentPane().setBackground(Color.DARK_GRAY);
		baseframe.setBackground(Color.DARK_GRAY);
		baseframe.setTitle("FileManager");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.BLACK);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				login.starter(); 
				 
			}
		});
		btnLogin.setBounds(74, 138, 89, 23);
		baseframe.getContentPane().add(btnLogin);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setForeground(Color.WHITE);
		btnAddUser.setBackground(Color.BLACK);
		btnAddUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				
				UserSet.addUser(JOptionPane.showInputDialog(" input "),"");
			}
		});
		btnAddUser.setBounds(126, 11, 106, 23);
		baseframe.getContentPane().add(btnAddUser);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.ORANGE);
		separator.setBounds(0, 119, 259, 56);
		baseframe.getContentPane().add(separator);
		
		JButton btnMergeUserPlaces = new JButton("merge user places");
		btnMergeUserPlaces.setBackground(Color.BLACK);
		btnMergeUserPlaces.setForeground(Color.WHITE);
		btnMergeUserPlaces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				//Merger.starter();
			}
		});
		btnMergeUserPlaces.setBounds(41, 79, 148, 23);
		baseframe.getContentPane().add(btnMergeUserPlaces);
		
		
	
	}
	
	public static void main(String argv[]) 
	{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Starter window = new Starter();
					window.baseframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}