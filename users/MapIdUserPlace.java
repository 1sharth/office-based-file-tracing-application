package users;
import global.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

import places.*;

public class MapIdUserPlace 
{

  public static String getUserName(String placename)
  {
	  String pquery="select username from userplaces where placename=?";
	  try 
	  {
		PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		st.setString(1,placename);
		ResultSet rs = st.executeQuery();
		rs.next();
		return rs.getString(1);
	  } 
	  catch (SQLException e) 
	  {
		e.printStackTrace();  
		return null;
	  }
  }
  public static String getPLaceName(String username)
  {
	  String pquery="select placename from userplaces where username=?";
	  try 
	  {
		PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		st.setString(1,username);
		ResultSet rs = st.executeQuery();
		rs.next();
		return rs.getString(1);
	  } 
	  catch (SQLException e) 
	  {
		e.printStackTrace();  
		return null;
	  }
  }
}
