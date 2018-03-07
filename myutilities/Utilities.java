package myutilities;
import global.Global;
import gui.*;

import java.awt.Component;
import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.AbstractButton;

import com.mysql.jdbc.PreparedStatement;

import users.UserFileSManager;
import Files.*;

public class Utilities 
{
  public static String[] listToStringArray(ArrayList<String> List)
  {
	  String Str[] = new String[List.size()];
	  for(int i=0; i<List.size(); i++)
		  Str[i]=List.get(i);
	  return Str;
  }
  
  public static String[] mapToStringArray(HashMap map)
  {
	  
	  Set<String> s = map.keySet();
	  String Str[] = new String[s.size()];
	  int i=0;
	  Iterator<String> it = s.iterator();
	  while(it.hasNext())
	  {
		Str[i++]=it.next();
	  } 
	  return Str;
  }
  
  public static String[] mapToStringArray_v(Map map)
  {
	  
	  Set<String> s=map.keySet();
	  String Str[] = new String[s.size()];
	  int i=0;
	  Iterator<String> it = s.iterator();
	  while(it.hasNext())
	  {
		String temp=it.next();
		File f=(File)map.get(temp);
		Str[i++]="Id: "+String.valueOf(f.getId())+" "+"Filename: "+f.getName()+"\nDestinationlist => "+
		           f.getDestlistraw()+'\n'+
				  "Last Created/Signed at: "+f.getFileLoc()+'\n';
	  } 
	  return Str;
  }
  public static boolean strValidation(String str)
  {
	  if(str.equals(""))
		  return false;
	  
	  if(str==null)
	      return false;
	  
	 	
	  char arr[]=str.toCharArray();
	  if(!((arr[0]>=65 && arr[0]<=90) || (arr[0]>=97 && arr[0]<=122)))
		  return false;
	 
	  for(int i=1; i<arr.length; i++)
		  if(!((arr[i]>=65 && arr[i]<=90) || (arr[i]>=97 && arr[i]<=122) || (arr[i]>=48 && arr[i]<=57) || arr[i]==' '))
		  {
			  return false;
		  }
	
	  return true;
  }
  public static boolean strNumValidation(String str)
  {
	  if(str.equals(""))
		  return false;
	  
	  if(str==null)
		  return false;
	  
	  for(int i=0; i<str.length(); i++)
	  {
		  if(!(str.charAt(i)>=48 && str.charAt(i)<=57))
			  return false;
	  }
	  return true;
  }
  public static boolean strIDValidation(String str)
  {
	  if(str.equals(""))
		  return false;
	  
	  if(str==null)
		  return false;
	  
	  if(str.length()!=10)
		  return false;
	  
	  char arr[]=str.toCharArray();
	  for(int i=1; i<arr.length; i++)
		  if(!((arr[i]>=65 && arr[i]<=90) || (arr[i]>=97 && arr[i]<=122) || (arr[i]>=48 && arr[i]<=57)))
		  {
			  return false;
		  }
	  HashMap m = (HashMap) Fileset.getMap();
	  if(!m.containsKey(str))
		  return false;
	  
	  return true;
  }
  public static void removeMinMaxClose(Component comp)
  {
    if(comp instanceof AbstractButton)
    {
      comp.getParent().remove(comp);
    }
    if (comp instanceof Container)
    {
      Component[] comps = ((Container)comp).getComponents();
      for(int x = 0, y = comps.length; x < y; x++)
      {
        removeMinMaxClose(comps[x]);
      }
    }
  }
  public static boolean isEmpty(ResultSet rs) throws SQLException
  {
	  int count=0;
	  while(rs.next())
	    count++;
	  if(count==0)
		  return true;
	  else
		  return false;
  }
  public static byte[] getWritableObj(Object obj)
  {
	  ByteArrayOutputStream  bos = new ByteArrayOutputStream();
      try 
      {
      	ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
	        oos.close();
	        bos.close();
		} catch (IOException e) {e.printStackTrace();}
      byte[] data = bos.toByteArray();
      return data;
  }
  
  /*
   *   returns {readable obj,Resultset }
   *   if result not found, returns null
   *   if result found but object is null,
   *   then returns {null,ResultSet containing null object}
   */
  public static Object readObj(ResultSet rs,int col) // returns {readable obj,Resultset }
  {
	
	  try
	  {
	  if(rs.getObject(col)==null)  
	    return null;
	  ObjectInputStream ins;
	  ByteArrayInputStream bais;
      Object o=null;
      
      bais = new ByteArrayInputStream(rs.getBytes(col));
      ins = new ObjectInputStream(bais);
      o=ins.readObject();
      ins.close();
      bais.close();
      //System.out.println(((T)o).i+" "+rs.getString(1));
      return o;
	  }
	  catch(SQLException e){e.printStackTrace(); return null;}
	  catch(IOException e){e.printStackTrace(); return null;}
	  catch(ClassNotFoundException e){e.printStackTrace(); return null;}
  }
}
