package places;
import global.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

import Alphanumeric.*;

public class Placeset
{
	public static String[] getPlaceMap()
	{
		String pquery="select placename from userplaces";
		ArrayList<String> S = new ArrayList<String>();
		try
		{
		  PreparedStatement st = (PreparedStatement) Global.conn.prepareStatement(pquery);
		  ResultSet rs = st.executeQuery();
	
		  
		  while(rs.next())
		  {
			S.add(rs.getString(1));
		  }
		}
		catch(SQLException e){e.printStackTrace();}
		String Arr[] =new String[S.size()];
		Arr=S.toArray(Arr);
		return Arr;
	}
}


