package users;


import global.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import myutilities.Utilities;
import Files.Fileset;

import com.mysql.jdbc.PreparedStatement;


public class UserFileSManager 
{
	private static ArrayList<Integer> FilesC;
	private static ArrayList<Integer> FilesS; 
     /* public static boolean addUser(String uname,String s)
	  {
    	  if(s.equals("S"))
    		  s="userwithfilestosign";
    	  else if(s.equals("C"))
    		  s="userwithexistingfiles";
    	  else
    	      {System.err.println("UserFileS:28:proper string not given");return false;}
    	  
    	  String pquery="insert into "+s+" (username) VALUES (?)";
    	  try 
    	  {
    		PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery);
    		st.setString(1,uname);
    		st.executeUpdate();
    	  } 
    	  catch (SQLException e) 
    	  {
    		e.printStackTrace();  
    		return false;
    	  }
    	  return true;
	  }
      */
	
	  /*
	   * takes a username..
	   * retrieves all files from the table user-files
	   * and adds these file ids to temporary arraylist MapS/C
	   * now all operations can be performed from this arraylist
	   * instead of slow database retrievals 
	   */
      public static int addUserFilesFromTabletoTemp(String username,String s)
      {
    	  if(s.equals("S"))
    		  s="userwithfilestosign";
    	  else if(s.equals("C"))
    		  s="userwithexistingfiles";
    	  else
    	      {
    		  System.out.println("error when uname="+username);
    		  System.err.println("UserFileS:59:proper string not given");return -1;
    		  }
    	  
    	  String pquery="select fileids from  "+s+"  where username=?";
		  try 
		  {
			PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st.setString(1,username);
			ResultSet rs = st.executeQuery();
			if(Utilities.isEmpty(rs))
			{
				return -1;
			}
		    rs.first();
			
			if(rs.getObject(1)==null && s.equals("userwithfilestosign"))
			{
				FilesS = new ArrayList<Integer>();
			}
			else if(rs.getObject(1)==null && s.equals("userwithexistingfiles"))
			{
				
				FilesC = new ArrayList<Integer>();
			}
			else if (s.equals("userwithfilestosign"))
			{
				FilesS = new ArrayList<Integer>();          //new arraylist
				FilesS = (ArrayList<Integer>)Utilities.readObj(rs, 1);
			}
			else if (s.equals("userwithexistingfiles"))
			{
				FilesC = new ArrayList<Integer>();         //new arraylist
				FilesC = (ArrayList<Integer>)Utilities.readObj(rs, 1);
			}
	                   //TODO: may cause problem
			
			
			return 0;
		  } 
		  catch (SQLException e) 
		  {
			e.printStackTrace();  
			return -1;
		  } 
      }
      
      public static void addAllUserTempDataToFile()
      {
    	  Fileset.addAllRelatedFilesFromTabletoTempData(FilesS,FilesC);
      }
      /*
       * in table userwithfilestosign, find the row with given username
       * update column containing arraylist of files 
       * by adding giving fileid to arraylist
       */
	  public static boolean addFileToUser(String uname,int fileid,String s)               
	  {
		  if(s.equals("S"))
    		  s="userwithfilestosign";
    	  else if(s.equals("C"))
    		  s="userwithexistingfiles";
    	  else
    	      {
    		  System.out.println("error when uname="+uname+" fileid="+fileid+" s="+s);
    		  System.err.println("UserFileS:59:proper string not given");return false;
    		  }
		  
		  String pquery="select username,fileids from  "+s+"  where username=?";
		  try 
		  {
			PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st.setString(1,uname);
			ResultSet rs = st.executeQuery();
			if(Utilities.isEmpty(rs))
			{
				System.out.println("UserFileS: 53: rs containing usern and fileid is empty");
				return false;
			}
		    rs.first();
			ArrayList<Integer> temp;
			if(rs.getObject(2)==null)
			{
				System.out.println("new arraylist created");
				temp = new ArrayList<Integer>();
			}
			else
			{
				temp = (ArrayList<Integer>)Utilities.readObj(rs, 2);
			}
	                   //TODO: may cause problem
			temp.add(fileid);
			System.out.println("temp contains at 0 "+temp.get(0));
			String insquery="update "+s+" set fileids=?";
			PreparedStatement st2 = (PreparedStatement) Global.conn.prepareStatement(insquery);
			st2.setObject(1,Utilities.getWritableObj(temp));
			st2.executeUpdate();
			/*rs.updateObject(2,Utilities.getWritableObj(temp));
			rs.rowUpdated();*/
		  } 
		  catch (SQLException e) 
		  {
			e.printStackTrace();  
			return false;
		  }
		  return true;
	  }
	  /*
	   * get individual destinations from destlist.
	   * then from table userplaces get corresponding usernames
	   * call addfiletouser function to add fileid to this user
	   * 
	   */
	  public static void mapper(String Destlist, int fileid)
	  {
		  System.out.println("destinations list= "+Destlist);
		  System.out.println("fileid= "+fileid);
		  System.out.printf("\n\n");
		  
		  char S[]=Destlist.toCharArray();
		  String place="",user="";
		  int i;
		  for(i=0; S[i]!=','; i++)             //this is creation place
			  ;
		  for(i=i+1; i<S.length; i++)
		  {
			  if(S[i]==',')
			  {
				  try
				  {
				  String pquery = "select username from userplaces where placename=?";
				  PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery);
				  st.setString(1,place);
				  ResultSet rs = st.executeQuery();
				  if(Utilities.isEmpty(rs))
					{
						System.out.println("UserFileS:107: rs  is empty");
						return;
					}
				  rs.first();
				  user=rs.getString(1);
				  }
				  catch(SQLException e){e.printStackTrace();}
				  
				  System.out.println("place= "+place+" user= "+user+" fileid= "+fileid);
				  UserFileSManager.addFileToUser(user, fileid,"userwithfilestosign");
				  
				  place="";
			  }
			  else
			  {
				  place+=S[i];
			  }
		  }
		  
		  try
		  {
		  String pquery = "select username from userplaces where placename=?";
		  PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery);
		  st.setString(1,place);
		  ResultSet rs = st.executeQuery();
		  if(Utilities.isEmpty(rs))
			{
				System.out.println("UserFileS: 134: rs is empty");
				return;
			}
		  rs.first();
		  user=rs.getString(1);
		  }
		  catch(SQLException e){e.printStackTrace();}
		  
		  System.out.println("place= "+place+" user= "+user+" fileid= "+fileid);
		  UserFileSManager.addFileToUser(user, fileid,"S");
	  }
	  /*
       * in table userwithfilestosign, find the row with given username
       * update column containing arraylist of files 
       * by deleting giving fileid to arraylist
       */
	  public static boolean deleteFileFromUser(String uname,int fileid,String s)
	  {
		  if(s.equals("S"))
    		  s="userwithfilestosign";
    	  else if(s.equals("C"))
    		  s="userwithexistingfiles";
    	  else
    	      {System.err.println("UserFileS:28:proper string not given");return false;}
		  
		  String pquery="select uesrname,fileids from  "+s+"  where username=?";
		  try
		  {
		    PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			st.setString(1,uname);
			ResultSet rs = st.executeQuery();
			if(Utilities.isEmpty(rs))
			{
				System.out.println("UserFileS: 160: rs containing usern and fileid is empty");
				return false;
			}
		    rs.first();
			ArrayList<Integer> temp=null;
			if(rs.getObject(2)==null)
			{
				System.out.println("UserFileS: 167: cant delete already null");
				return false;
			}
			else
			{
				temp = (ArrayList<Integer>)Utilities.readObj(rs, 2);
			}
	                   //TODO: may cause problem
			temp.remove(fileid);
			rs.updateObject(2,Utilities.getWritableObj(temp));
			rs.rowUpdated();
		  }
		  catch(SQLException e){e.printStackTrace();}
		  
		  return true;
	  }
	  
	  public static Integer[] getFileIds(String uname,String s)
	  {
		  ArrayList<Integer> FilesZ=null;
		  if(s.equals("S"))
    		  {
			    s="userwithfilestosign";
			    FilesZ=FilesS;
    		  }
    	  else if(s.equals("C"))
    		  {
    		    s="userwithexistingfiles";
    		    FilesZ=FilesC;
    		  }
    	  else
    	      {System.err.println("UserFileS:290:proper string not given");}
		  
		  if(FilesZ.size()==0)
			  return null;
		  else
		  {
			  Integer arr[] = new Integer[FilesZ.size()];
			  arr = FilesZ.toArray(arr);
			  return arr;
		  }
	  }
	  
	  public static int getNoFiles(String uname,String s)
	  {
		  ArrayList<Integer> FilesZ=null;
		  if(s.equals("S"))
    		  {
			    s="userwithfilestosign";
			    FilesZ=FilesS;
    		  }
    	  else if(s.equals("C"))
    		  {
    		    s="userwithexistingfiles";
    		    FilesZ=FilesC;
    		  }
    	  else
    	      {System.err.println("UserFileS:290:proper string not given");}
		  
		  return FilesZ.size();
	  }
}
