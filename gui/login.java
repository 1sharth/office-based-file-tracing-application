package gui;

import users.UserFileSManager;
import global.Global;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.mysql.jdbc.PreparedStatement;

import myutilities.Utilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * NOTE:
 * every thing that happens after login can be made more efficient by just searching username
 * in this class for 1st time and noticing its index. then this index can be given further 
 * to directly retrieve user information by using index number instead of searching for user again and again.
 * But also notice that it maybe unsafe. because after obtaining index, if user waits or remain idle and some
 * one updates the tables changing the actual index no then current user will get different results when
 * he chooses to operate on the info associated at that index no.
 */


public class login
{
	Baseframe loginframe = new Baseframe(325,196);
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	
	public static void starter()
	{
		new login();
	}
    
	public boolean correctCredentials(String username,String password)
	{
		 String pquery="select password from userplaces where username=?";
		  try 
		  {
			PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st.setString(1,username);
			ResultSet rs = st.executeQuery();
			if(Utilities.isEmpty(rs))
			{
				return false;
			}
	
			rs.first();
			if(password.equals(rs.getString(1)))
			{
				return true;
			}
			else
			{
				return false;
			}
		  } 
		  catch (SQLException e) 
		  {
			e.printStackTrace();  
			return false;
		  }
	}
	
	public login() 
	{
		loginframe.getContentPane().setBackground(new Color(102, 102, 102));
		
		textField = new JTextField();
		textField.setBounds(145, 43, 107, 20);
		loginframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(62, 46, 60, 14);
		loginframe.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(62, 77, 60, 14);
		loginframe.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(145, 74, 107, 20);
		loginframe.getContentPane().add(passwordField);
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String usern=textField.getText();
				String pass=String.valueOf(passwordField.getPassword());  
				if(correctCredentials(usern,pass))            //TODO: handle
				{
					System.out.println("correct");
					TraceCreate.starter(usern); 
				}
				else
				{
					System.out.println("incorrect");
				}
				loginframe.dispose();
				//TraceCreate.starter(usern);                         
			}
		});
		btnSubmit.setBounds(145, 109, 92, 23);
		loginframe.getContentPane().add(btnSubmit);
		loginframe.setTitle("Login");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
	}
}
