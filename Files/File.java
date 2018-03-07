package Files;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import places.Place;
import places.Placeset;
import places.TimePlace;

public class File implements Serializable
{
	private String name;
	private int id;
	private ArrayList<String> Destlist=new ArrayList<String>();
	private ArrayList<String> timelist=new ArrayList<String>();
	private String Destlistraw;
	private long currpos;
	private String Creation_place;
	
	public File(String name)
	{
		this.name=name.toString();
		currpos=1;
	}
	public void addDestinations(String list)
	{
		int i;
		Creation_place="";
		for(i=0; list.charAt(i)!=','; i++)
			Creation_place+=list.charAt(i);
		i++;
		Destlistraw="";
		
		for(int j=i; j<list.length(); j++)
		  Destlistraw+=list.charAt(j);
		
		String arr[]= list.split(",");
		for(int k=0; k<arr.length; k++)         //arr[k] is name of place??
		{
			Destlist.add(arr[k]);
		}
		//Destlist.get(0).setTime();
		timelist.add((new Date()).toString());
	}
	

	public String getFileLoc()                                //returns loc 
	{
		return Destlist.get((int)currpos-1);                       
	}
	
	public String getFileTime()
	{
		return timelist.get((int)currpos-1);
	}
	
	public boolean increaseCurrPos()
	{
		timelist.add((new Date()).toString());                                   //long to int
		currpos++;                              //currpos points to place where file has to reach/signed yet
		if(currpos>Destlist.size()-1)                             //>=
		{
			System.out.print(this.name+" has been signed at its last destination at ");
			System.out.println(Destlist.get((int)currpos-1));
			return false;
		}
		else
		{
			 
			return true;
		}	
		
	}
	
	public String getHistory()
	{
		String S="";
		for(int i=0; i<(int)currpos; i++)
		{
			if(i==0)
			{	
			  S+="Created at ";
			}
			else
			{
			  S+="Reached at ";
			}
			S+=Destlist.get(i);
			
			S+="\nat time: "+ timelist.get(i);
			S+="\n\n";
		}
		
		S+="Yet to reach\n\n";
		for(int i=(int)currpos; i<Destlist.size(); i++)
		{
			S+=Destlist.get(i);
			S+="\n";
		}
	   
		return S;
	}
	
	public String getName(){return name;}
	public int getId(){return id;}
	public void setId(int idd){id=idd;}
	public long getCurrPos(){return currpos;}
	public String getDestlistraw(){return Destlistraw;}
	//public String getDestPlaceAt(long ind){return Destlist.get((int)ind);}
	public String getNextPlace()
	{
		if(currpos <= Destlist.size())
			return Destlist.get((int) currpos);
		else
			return "";
	}
}