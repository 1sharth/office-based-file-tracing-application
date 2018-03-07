package places;

public class Place
{
	private String id,name,loc;
	public Place(String name,String loc)
	{
		this.name=name.toString();
		this.loc=loc.toString();
	}
	public String getName(){return name;}
	public String getLoc(){return loc;}
	public String getId(){return id;}
	public void setId(String str){id=str.toString();}
}