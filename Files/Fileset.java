package Files;
import global.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import myutilities.Utilities;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import Alphanumeric.*;
import places.*;


/*
 * THE MODEL:
 * instead of repeatedly retrieving fileobject with help of fileids from database
 * lets just add only NEEDED file objects to temporary data structure storage and all
 * operations will be performed from there.
 * 
 * Assumption: for minimizing size of temporary data structure only NEEDED information 
 * will be stored in it. Needed information is assumed to be consists of all files
 * that the current logged in user is related to ie in tables userwithexistingfiles
 * and userwithfilestosign
 */


public class Fileset
{
	/*
	 * create the file object.
	 * add a new row to table files with fileid  and fileobject
	 */
	static int idgen;
	private static Map<Integer,File> map;
	//private static AlphaNumeric idgen = new AlphaNumeric();
	public static int addFile(String name,String destlist)             //TODO: idgen lookup
	{
		File file=new File(name);    
		file.addDestinations(destlist);
		file.setId(idgen++);
		try 
		  {
			String pquery="insert into files (fileobject) values(?)";
			PreparedStatement st=(PreparedStatement) Global.conn.prepareStatement(pquery);
			st.setObject(1,Utilities.getWritableObj(file));
			st.executeUpdate();
			pquery="";
		  } 
		  catch (SQLException e){e.printStackTrace();}
		
		return file.getId();
	}
	
	//adds related files to temporary data structure
	public static void addAllRelatedFilesFromTabletoTempData(ArrayList<Integer> FilesS,ArrayList<Integer> FilesC)
	{
       addthesefiles("userwithexistingfiles",FilesC);
       addthesefiles("userwithfilestosign",FilesS);	  
	}
	
	public static void addthesefiles(String s,ArrayList<Integer> FilesZ)
	{
		try
		{
		  int fileid; String pquery;
		  map = new HashMap<Integer,File>();
		  
		  for(int i=0; i<FilesZ.size(); i++)
		  {
			 fileid=FilesZ.get(i);
			 pquery="select fileobject from files where fileid=?";
			 PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery);
			 st.setInt(1,fileid);
			 ResultSet rs = st.executeQuery();
			 if(Utilities.isEmpty(rs))
			 {
				 System.err.println("FileSet:addthesefiles:rs is empty: means file wasnt added or user contains extra files");
				 return;
			 }
			 rs.first();
			 if(rs.getObject(1)==null)
			 {
				 System.err.println("FileSet:addthesefiles:Fileobject is null: means file wasnt added or user contains extra files");
				 return;
			 }
			 File fileobj = (File)Utilities.readObj(rs,1);
			 map.put(fileid,fileobj);
		  }
		}
		catch(SQLException e){e.printStackTrace();}
	}
	
	public static String traceFileLoc(int id)
	{
		return map.get(id).getFileLoc()+" "+map.get(id).getFileTime();
	}
	
	public static void reachNextDest(int id)
	{
		if(!map.get(id).increaseCurrPos())
		{
	
		}
	}
	
	public static long getCurrPos(int id)
	{
		return map.get(id).getCurrPos();                
	}
	public static File getFile(int id){return map.get(id);}
	public static Map getMap(){return map;}
	public static String getHistory(int id){return map.get(id).getHistory();}
	public static String getNextPlace(int id){return map.get(id).getNextPlace();}
}