package Alphanumeric;

import java.io.Serializable;

public class AlphaNumeric implements Serializable
{
	private String value;
	public AlphaNumeric()
	{
		value="0000000000";
	}
	
	public void increament()
	{
		char arr[]=value.toCharArray();
		incr(arr,0);
	}
	
	public String getValue(){return value;}
	
	public void incr(char arr[],int l)
	{
		char v=arr[value.length()-1-l];
		if(v=='9')
			arr[value.length()-1-l]='A';
		else if(v=='Z')
		    arr[value.length()-1-l]='a';
		else if(v=='z')
		{
			if(arr[value.length()-1-l]=='z')                     //keep in mind of limit
			{   arr[value.length()-1-l]='0';
				incr(arr,++l);
			}
		}
		else
		{   v+=1;
			arr[value.length()-1-l]=v;
		}
		value=String.copyValueOf(arr);
	}
	
}
