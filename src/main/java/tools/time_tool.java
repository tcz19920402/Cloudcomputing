package tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class time_tool {
	public String get_time() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public void print_time() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(new Date()));
	}

	public long get_time_long(String time_string) {
		time_string=time_string.replaceAll("-", "");
		time_string=time_string.replaceAll(":", "");
		time_string=time_string.replaceAll(" ", "");
		return Long.valueOf(time_string);
	}
	
	public long get_time_gap(String time_start,String time_end){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff = 0;
		try
		{
		    Date d1 = df.parse(time_end);
		    Date d2 = df.parse(time_start);
		    diff = d1.getTime() - d2.getTime();
		   
		}
		catch (Exception e)
		{
		}
		 long sec = diff / (1000 );
		 return sec;
		
	}
}
