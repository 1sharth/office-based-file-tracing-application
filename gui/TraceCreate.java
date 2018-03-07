package gui;

import users.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import Files.Fileset;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import myutilities.Utilities;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;


public class TraceCreate
{
    Baseframe tcframe;
    private static String uname;
    private static int index;
    private static int nofilesC;
    private static int nofilesS;
	
	public static void starter(String name) 
	{
		uname=name;
		UserFileSManager.addUserFilesFromTabletoTemp(uname,"S");
		UserFileSManager.addUserFilesFromTabletoTemp(uname,"C");
		nofilesC=UserFileSManager.getNoFiles(uname,"C");
		nofilesS=UserFileSManager.getNoFiles(uname,"S");
		//handle
		UserFileSManager.addAllUserTempDataToFile();
		new TraceCreate();
	}

	/**
	 * Create the frame.
	 */

	
	public TraceCreate() 
	{
		tcframe = new Baseframe(490,485);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 452, 69);
		tcframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome, "+uname);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(10, 11, 418, 14);
		panel.add(lblWelcome);
		
		JButton btnCreateFile = new JButton("Create File");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				tcframe.setEnabled(false);
				addfileframe.starter(uname);
				
				tcframe.setEnabled(true);
			}
		});
		btnCreateFile.setBounds(170, 35, 97, 23);
		panel.add(btnCreateFile);
		
		CPanel P[] = new CPanel[nofilesC];                  //no of createdfiles
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 324, 319);
		tcframe.getContentPane().add(scrollPane);
		
		JPanel spanel = new JPanel();
		scrollPane.setViewportView(spanel);
	    spanel.setLayout(new GridLayout(nofilesC==0?1:nofilesC, 0, 0, 0));                
	    //spanel.setLayout(new GridLayout(1, 0, 0, 0));
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
	    panel_1.setBounds(339, 91, 123, 191);
	    tcframe.getContentPane().add(panel_1);
	    panel_1.setLayout(null);
	    
	    JLabel lblFilesTo = new JLabel(" Files to Sign");
	    lblFilesTo.setBounds(10, 5, 103, 14);
	    panel_1.add(lblFilesTo);
	    
	    
	    
	    JButton btnRefresh = new JButton("Refresh");
	    btnRefresh.setBounds(344, 338, 89, 22);
	    tcframe.getContentPane().add(btnRefresh);
	    btnRefresh.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent arg0) 
	    	{
	    		tcframe.setEnabled(false);
	    		TraceCreate.starter(uname);
	    		tcframe.dispose();
	    	}
	    });
		
	    Integer Files[]=UserFileSManager.getFileIds(uname,"C");
	    if(Files==null)
	    	{
	    	  System.out.println("trace create: line 129: Files==null");
	    	}
	    
		for(int k=1; k<=nofilesC; k++)
		{
			P[k-1] = new CPanel(Files[k-1].toString());                                     //panel created
			
			String S=""; String fileid="";
			S=Files[k-1].toString();
			fileid=S.toString();                                       //TODO:look
			
			S+="  ";
			S+=Fileset.getFile(Integer.valueOf(fileid)).getName();
			
			P[k-1].fileidname.setText(S);
			S="";
			
			if(Fileset.getCurrPos(Integer.valueOf(fileid))==1)
			{
				S+="Created at: ";
			}
			else
			{
				S+="Last signed at: ";
			}
			
		
			P[k-1].fileplace.setText(S+Fileset.getFile(Integer.valueOf(fileid)).getFileLoc());
			P[k-1].filetime.setText(S+Fileset.getFile(Integer.valueOf(fileid)).getFileTime());
		
			
			
			spanel.add(P[k-1]);                                              //panel added
		}
		
		//int nofiletosign=UserFileSManager.getNoFiles(uname,"S");
		Integer FilesToSign[] = UserFileSManager.getFileIds(uname,"S");
		//System.out.println(MapIdUF.MapD.get(index).get(0)+":");
		//System.out.println(MapIdUserFileSign.MapS.get(index).get(0)+":");
		Integer A[]=new Integer[nofilesS];
		int Aindex=0;
		for(int k=1; k<=nofilesS; k++)          //only allow file to sign if user chance to sign
		{
			String placename=Fileset.getNextPlace(FilesToSign[k-1]);
		
			if(!uname.equals("") && uname.equals(MapIdUserPlace.getUserName(placename)))
			   A[Aindex++]=(FilesToSign[k-1]);
		}
		
		
		
		final JComboBox CB = new JComboBox(A);
	    CB.setBounds(10, 30, 103, 20);
	    panel_1.add(CB);
	    
	    JButton btnSign = new JButton("Sign");
	    btnSign.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) 
	    	{   
	    		String id=(String) CB.getItemAt(CB.getSelectedIndex());
	    		if(Utilities.strIDValidation(id))
	    		    {
	    			  Fileset.reachNextDest(Integer.valueOf(id));
	    			  UserFileSManager.deleteFileFromUser(uname,Integer.valueOf(id),"S");
	    		    }
					else if(id!=null && !Fileset.getMap().containsKey(id))
					{
						JOptionPane.showMessageDialog(tcframe,"ID doesnt exist","Try Again",JOptionPane.WARNING_MESSAGE);
					}
					else if(id!=null)
					{
						JOptionPane.showMessageDialog(tcframe,"Improper ID: only Alphanumerics allowed","Try Again",JOptionPane.WARNING_MESSAGE);
					}
	    	}
	    });
	    btnSign.setHorizontalAlignment(SwingConstants.LEFT);
	    btnSign.setBounds(10, 121, 60, 23);
	    panel_1.add(btnSign);
		
		/*int nofiletosign=MapIdUserFileSign.MapS.get(index).size()-1;
		System.out.println(MapIdUF.MapD.get(index).get(0)+":");
		System.out.println(MapIdUserFileSign.MapS.get(index).get(0)+":");
		for(int k=1; k<=nofiletosign; k++)
		{
			System.out.println(MapIdUserFileSign.MapS.get(index).get(k));
		}*/

	}
}
