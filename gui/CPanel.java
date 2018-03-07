package gui;

import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import Files.Fileset;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CPanel extends JPanel 
{
	private String fileid;
    public JLabel fileidname,fileplace,filetime;
	public CPanel(final String fileid) 
	{
		this.fileid = fileid;
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.GREEN, null, null, null));
		setLayout(new GridLayout(4, 0, 0, 0));
		
		fileidname = new JLabel();
		add(fileidname);
		
		fileplace = new JLabel();
		add(fileplace);
		
		filetime = new JLabel();
		add(filetime);
		
		final JPanel panel = new JPanel();
		add(panel);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		panel.setLayout(fl_panel);
		
		JButton btnNewButton = new JButton("Show Full Info");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(panel,Fileset.getHistory(fileid));
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete File");
		panel.add(btnNewButton_1);

	}

}
