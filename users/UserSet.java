package users;
import java.util.HashMap;
import java.util.Map;

import Alphanumeric.*;
import Files.File;

class User
{
  String id;
  String username;
  String password;
  
  User(String id,String name,String pass)
  {this.id=id; username=name; password=pass;}
  

  String getId(){return id;}
}

public class UserSet                          //associated with place
{
	private static AlphaNumeric idgen = new AlphaNumeric();
	public static String addUser(String name,String pass)
	{
		User user = new User(idgen.getValue(),name,pass);
		UserFileSManager.addUser(name,"C");
		UserFileSManager.addUser(name,"S");
		idgen.increament();
		return user.getId();
	}	
}

