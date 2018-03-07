package places;

import java.io.Serializable;
import java.util.Date;

public class TimePlace implements Serializable
{
	private String time;                                        //may have to change this
	private String placename;
	public TimePlace(String name)
	{
		placename=name;
		time=(new Date()).toString();
	}
	public void setTime()
	{time=(new Date()).toString();}
	public String getTime(){return time;}
	public String getPlaceName(){return placename;}
}